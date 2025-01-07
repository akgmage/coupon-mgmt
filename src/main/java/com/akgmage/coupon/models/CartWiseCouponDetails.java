package com.akgmage.coupon.models;

import lombok.Data;

@Data
public class CartWiseCouponDetails extends CouponDetails {
    private double threshold;
    private double discount;
}