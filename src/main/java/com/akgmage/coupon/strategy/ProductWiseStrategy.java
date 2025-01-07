package com.akgmage.coupon.strategy;

import com.akgmage.coupon.models.Cart;
import com.akgmage.coupon.models.ProductWiseCouponDetails;
import org.springframework.stereotype.Component;

@Component
public class ProductWiseStrategy implements CouponStrategy {
    private final ProductWiseCouponDetails details;

    public ProductWiseStrategy(ProductWiseCouponDetails details) {
        this.details = details;
    }

    @Override
    public double calculateDiscount(Cart cart) {
        return cart.getItems().stream()
                .filter(item -> item.getProductId().equals(details.getProductId()))
                .mapToDouble(item -> (item.getPrice() * item.getQuantity() * details.getDiscount()) / 100)
                .sum();
    }

    @Override
    public Cart applyDiscount(Cart cart) {
        cart.getItems().forEach(item -> {
            if (item.getProductId().equals(details.getProductId())) {
                double itemDiscount = (item.getPrice() * item.getQuantity() * details.getDiscount()) / 100;
                item.setTotalDiscount(itemDiscount);
            }
        });

        double totalDiscount = calculateDiscount(cart);
        cart.setTotalDiscount(totalDiscount);
        cart.setFinalPrice(cart.getTotalPrice() - totalDiscount);
        return cart;
    }
}

