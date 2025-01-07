package com.akgmage.coupon.models;

// Utility class for converting between entities and DTOs
public class CouponMapper {
    public static CouponDTO toDTO(Coupon coupon) {
        if (coupon == null) return null;

        CouponDTO dto = new CouponDTO();
        dto.setId(coupon.getId());
        dto.setType(coupon.getType());
        dto.setActive(coupon.isActive());
        dto.setExpiryDate(coupon.getExpiryDate());
        dto.setDetails(toCouponDetailsDTO(coupon.getDetails()));

        return dto;
    }

    public static CouponDetailsDTO toCouponDetailsDTO(CouponDetails details) {
        if (details == null) return null;

        CouponDetailsDTO dto = new CouponDetailsDTO();
        dto.setType(details.getType());

        switch (details.getType()) {
            case CART_WISE:
                CartWiseCouponDetails cartDetails = (CartWiseCouponDetails) details;
                dto.setThreshold(cartDetails.getThreshold());
                dto.setDiscountPercentage(cartDetails.getDiscount());
                break;

            case PRODUCT_WISE:
                ProductWiseCouponDetails productDetails = (ProductWiseCouponDetails) details;
                dto.setProductId(productDetails.getProductId());
                dto.setProductDiscount(productDetails.getDiscount());
                break;

            case BXGY:
                BxGyCouponDetails bxgyDetails = (BxGyCouponDetails) details;
                dto.setBuyProducts(bxgyDetails.getBuyProducts().stream()
                        .map(CouponMapper::toBxGyProductDTO)
                        .toList());
                dto.setGetProducts(bxgyDetails.getGetProducts().stream()
                        .map(CouponMapper::toBxGyProductDTO)
                        .toList());
                dto.setRepetitionLimit(bxgyDetails.getRepetitionLimit());
                break;
        }

        return dto;
    }

    public static BxGyProductDTO toBxGyProductDTO(BxGyProduct product) {
        if (product == null) return null;
        return new BxGyProductDTO(product.getProductId(), product.getQuantity());
    }

    public static Coupon toEntity(CouponDTO dto) {
        if (dto == null) return null;

        Coupon coupon = new Coupon();
        coupon.setId(dto.getId());
        coupon.setType(dto.getType());
        coupon.setActive(dto.isActive());
        coupon.setExpiryDate(dto.getExpiryDate());
        coupon.setDetails(toCouponDetails(dto.getDetails()));

        return coupon;
    }

    public static CouponDetails toCouponDetails(CouponDetailsDTO dto) {
        if (dto == null) return null;

        return switch (dto.getType()) {
            case CART_WISE -> {
                CartWiseCouponDetails details = new CartWiseCouponDetails();
                details.setThreshold(dto.getThreshold());
                details.setDiscount(dto.getDiscountPercentage());
                yield details;
            }
            case PRODUCT_WISE -> {
                ProductWiseCouponDetails details = new ProductWiseCouponDetails();
                details.setProductId(dto.getProductId());
                details.setDiscount(dto.getProductDiscount());
                yield details;
            }
            case BXGY -> {
                BxGyCouponDetails details = new BxGyCouponDetails();
                details.setBuyProducts(dto.getBuyProducts().stream()
                        .map(CouponMapper::toBxGyProduct)
                        .toList());
                details.setGetProducts(dto.getGetProducts().stream()
                        .map(CouponMapper::toBxGyProduct)
                        .toList());
                details.setRepetitionLimit(dto.getRepetitionLimit());
                yield details;
            }
        };
    }

    public static BxGyProduct toBxGyProduct(BxGyProductDTO dto) {
        if (dto == null) return null;
        BxGyProduct product = new BxGyProduct();
        product.setProductId(dto.getProductId());
        product.setQuantity(dto.getQuantity());
        return product;
    }
}
