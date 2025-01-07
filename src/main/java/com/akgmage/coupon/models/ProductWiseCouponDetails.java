package com.akgmage.coupon.models;

import lombok.Data;

@Data
public class ProductWiseCouponDetails extends CouponDetails {
    private Long productId;
    private double discount;
}
