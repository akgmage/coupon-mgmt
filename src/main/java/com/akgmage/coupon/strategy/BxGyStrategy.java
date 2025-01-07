package com.akgmage.coupon.strategy;

import com.akgmage.coupon.models.BxGyCouponDetails;
import com.akgmage.coupon.models.Cart;
import com.akgmage.coupon.models.CartItem;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BxGyStrategy implements CouponStrategy {
    private final BxGyCouponDetails details;

    public BxGyStrategy(BxGyCouponDetails details) {
        this.details = details;
    }

    @Override
    public double calculateDiscount(Cart cart) {
        int applicableTimes = calculateApplicableTimes(cart);
        return calculateTotalDiscount(cart, applicableTimes);
    }

    private int calculateApplicableTimes(Cart cart) {
        Map<Long, Integer> cartProducts = cart.getItems().stream()
                .collect(Collectors.toMap(CartItem::getProductId, CartItem::getQuantity));

        int maxApplications = details.getBuyProducts().stream()
                .map(buyProduct -> {
                    Integer cartQuantity = cartProducts.getOrDefault(buyProduct.getProductId(), 0);
                    return cartQuantity / buyProduct.getQuantity();
                })
                .min(Integer::compareTo)
                .orElse(0);

        return Math.min(maxApplications, details.getRepetitionLimit());
    }

    private double calculateTotalDiscount(Cart cart, int applicableTimes) {
        return details.getGetProducts().stream()
                .mapToDouble(product -> {
                    Optional<CartItem> cartItem = cart.getItems().stream()
                            .filter(item -> item.getProductId().equals(product.getProductId()))
                            .findFirst();

                    return cartItem.map(item ->
                            item.getPrice() * product.getQuantity() * applicableTimes
                    ).orElse(0.0);
                })
                .sum();
    }

    @Override
    public Cart applyDiscount(Cart cart) {
        int applicableTimes = calculateApplicableTimes(cart);
        double totalDiscount = calculateTotalDiscount(cart, applicableTimes);

        // Apply discounts to free products
        details.getGetProducts().forEach(product -> {
            cart.getItems().stream()
                    .filter(item -> item.getProductId().equals(product.getProductId()))
                    .findFirst()
                    .ifPresent(item -> {
                        double itemDiscount = item.getPrice() * product.getQuantity() * applicableTimes;
                        item.setTotalDiscount(itemDiscount);
                    });
        });

        cart.setTotalDiscount(totalDiscount);
        cart.setFinalPrice(cart.getTotalPrice() - totalDiscount);
        return cart;
    }
}
