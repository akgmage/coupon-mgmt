package com.akgmage.coupon.controller;

import com.akgmage.coupon.models.Cart;
import com.akgmage.coupon.models.Coupon;
import com.akgmage.coupon.models.CouponDTO;
import com.akgmage.coupon.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        return ResponseEntity.ok(couponService.createCoupon(coupon));
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        return couponService.getCouponById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        return ResponseEntity.ok(couponService.updateCoupon(id, coupon));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/applicable-coupons")
    public ResponseEntity<List<CouponDTO>> getApplicableCoupons(@RequestBody Cart cart) {
        return ResponseEntity.ok(couponService.getApplicableCoupons(cart));
    }

    @PostMapping("/apply-coupon/{id}")
    public ResponseEntity<Cart> applyCoupon(@PathVariable Long id, @RequestBody Cart cart) {
        return ResponseEntity.ok(couponService.applyCoupon(id, cart));
    }
}
