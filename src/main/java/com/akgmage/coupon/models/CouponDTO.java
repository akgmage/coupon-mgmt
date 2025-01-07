package com.akgmage.coupon.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
    private Long id;
    private CouponType type;
    private CouponDetailsDTO details;
    private boolean active;
    private double discount;
    private LocalDateTime expiryDate;

    public CouponDTO(Long id, CouponType type, double discount) {
    }
}

