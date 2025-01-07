package com.akgmage.coupon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Response DTO for applicable coupons endpoint
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicableCouponDTO {
    private Long couponId;
    private CouponType type;
    private double potentialDiscount;
    private String description;
}
