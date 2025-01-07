package com.akgmage.coupon.models;

import lombok.Data;

import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private double totalPrice;
    private double totalDiscount;
    private double finalPrice;
}
