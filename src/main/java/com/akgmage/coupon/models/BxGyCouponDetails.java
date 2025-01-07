package com.akgmage.coupon.models;

import lombok.Data;

import java.util.List;

@Data
public class BxGyCouponDetails extends CouponDetails {
    private List<BxGyProduct> buyProducts;
    private List<BxGyProduct> getProducts;
    private int repetitionLimit;
}
