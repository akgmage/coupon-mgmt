package com.akgmage.coupon.service;

import com.akgmage.coupon.models.*;
import com.akgmage.coupon.repository.CouponRepository;
import com.akgmage.coupon.strategy.BxGyStrategy;
import com.akgmage.coupon.strategy.CartWiseStrategy;
import com.akgmage.coupon.strategy.CouponStrategy;
import com.akgmage.coupon.strategy.ProductWiseStrategy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CouponService {
    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }

    public Coupon updateCoupon(Long id, Coupon coupon) {
        if (!couponRepository.existsById(id)) {
            throw new RuntimeException("Coupon not found");
        }
        coupon.setId(id);
        return couponRepository.save(coupon);
    }

    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    public List<CouponDTO> getApplicableCoupons(Cart cart) {
        List<Coupon> allCoupons = couponRepository.findAll();
        return allCoupons.stream()
                .map(coupon -> {
                    CouponStrategy strategy = createStrategy(coupon);
                    double discount = strategy.calculateDiscount(cart);
                    return new CouponDTO(coupon.getId(), coupon.getType(), discount);
                })
                .filter(dto -> dto.getDiscount() > 0)
                .collect(Collectors.toList());
    }

    public Cart applyCoupon(Long couponId, Cart cart) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        CouponStrategy strategy = createStrategy(coupon);
        return strategy.applyDiscount(cart);
    }

    private CouponStrategy createStrategy(Coupon coupon) {
        return switch (coupon.getType()) {
            case CART_WISE -> new CartWiseStrategy((CartWiseCouponDetails) coupon.getDetails());
            case PRODUCT_WISE -> new ProductWiseStrategy((ProductWiseCouponDetails) coupon.getDetails());
            case BXGY -> new BxGyStrategy((BxGyCouponDetails) coupon.getDetails());
        };
    }
}
