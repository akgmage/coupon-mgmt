package com.akgmage.coupon.strategy;

import com.akgmage.coupon.models.Cart;

public interface CouponStrategy {
    double calculateDiscount(Cart cart);
    Cart applyDiscount(Cart cart);
}
