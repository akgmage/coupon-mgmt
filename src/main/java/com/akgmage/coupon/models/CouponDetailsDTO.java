package com.akgmage.coupon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDetailsDTO {
    private CouponType type;

    // For CartWise coupons
    private Double threshold;
    private Double discountPercentage;

    // For ProductWise coupons
    private Long productId;
    private Double productDiscount;

    // For BxGy coupons
    private List<BxGyProductDTO> buyProducts;
    private List<BxGyProductDTO> getProducts;
    private Integer repetitionLimit;
}
