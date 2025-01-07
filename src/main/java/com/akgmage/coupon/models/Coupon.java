package com.akgmage.coupon.models;

import com.akgmage.coupon.converter.CouponDetailsConverter;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType type;

    @Column(columnDefinition = "json")
    @Convert(converter = CouponDetailsConverter.class)
    private CouponDetails details;

    @Column(nullable = false)
    private boolean active = true;

    @Column
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}