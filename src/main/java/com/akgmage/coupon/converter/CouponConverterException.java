package com.akgmage.coupon.converter;

// Custom exception for converter errors
public class CouponConverterException extends RuntimeException {
    public CouponConverterException(String message) {
        super(message);
    }

    public CouponConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
