package com.akgmage.coupon.strategy;

import com.akgmage.coupon.models.Cart;
import com.akgmage.coupon.models.CartWiseCouponDetails;
import org.springframework.stereotype.Component;

@Component
public class CartWiseStrategy implements CouponStrategy {
    private final CartWiseCouponDetails details;

    public CartWiseStrategy(CartWiseCouponDetails details) {
        this.details = details;
    }

    @Override
    public double calculateDiscount(Cart cart) {
        double totalAmount = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        if (totalAmount > details.getThreshold()) {
            return totalAmount * (details.getDiscount() / 100);
        }
        return 0;
    }

    @Override
    public Cart applyDiscount(Cart cart) {
        double discount = calculateDiscount(cart);
        cart.setTotalDiscount(discount);
        cart.setFinalPrice(cart.getTotalPrice() - discount);
        return cart;
    }
}
