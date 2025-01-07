package com.akgmage.coupon.models;

import lombok.Data;

@Data
public class CartItem {
    private Long productId;
    private int quantity;
    private double price;
    private double totalDiscount;
}