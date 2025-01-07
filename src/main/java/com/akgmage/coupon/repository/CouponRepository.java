package com.akgmage.coupon.repository;


import com.akgmage.coupon.models.Coupon;
import com.akgmage.coupon.models.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    // Basic queries using method names
    List<Coupon> findByType(CouponType type);

    List<Coupon> findByActive(boolean active);

    List<Coupon> findByTypeAndActive(CouponType type, boolean active);

    // For product-wise coupons
    @Query(value = "SELECT c.* FROM coupon c WHERE c.type = 'PRODUCT_WISE' " +
            "AND JSON_EXTRACT(c.details, '$.productId') = :productId " +
            "AND c.active = true", nativeQuery = true)
    List<Coupon> findProductSpecificCoupons(@Param("productId") Long productId);

    // For cart-wise coupons
    @Query(value = "SELECT c.* FROM coupon c WHERE c.type = 'CART_WISE' " +
            "AND CAST(JSON_EXTRACT(c.details, '$.threshold') AS DECIMAL) <= :amount " +
            "AND c.active = true", nativeQuery = true)
    List<Coupon> findApplicableCartWiseCoupons(@Param("amount") double cartAmount);

    // For BxGy coupons
    @Query(value = "SELECT c.* FROM coupon c WHERE c.type = 'BXGY' " +
            "AND JSON_EXTRACT(c.details, '$.buyProducts') LIKE %:productId% " +
            "AND c.active = true", nativeQuery = true)
    List<Coupon> findBxGyCouponsForProduct(@Param("productId") Long productId);

    // For expiring coupons
    List<Coupon> findByExpiryDateBetweenAndActive(LocalDateTime start, LocalDateTime end, boolean active);

    // Count queries
    long countByType(CouponType type);
    long countByActive(boolean active);
}