package com.akgmage.coupon.converter;

import com.akgmage.coupon.converter.CouponConverterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.akgmage.coupon.models.CouponDetails;
import com.akgmage.coupon.models.CartWiseCouponDetails;
import com.akgmage.coupon.models.ProductWiseCouponDetails;
import com.akgmage.coupon.models.BxGyCouponDetails;

@Converter
public class CouponDetailsConverter implements AttributeConverter<CouponDetails, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CouponDetails couponDetails) {
        try {
            return objectMapper.writeValueAsString(couponDetails);
        } catch (JsonProcessingException e) {
            throw new CouponConverterException("Error converting coupon details to JSON", e);
        }
    }

    @Override
    public CouponDetails convertToEntityAttribute(String jsonString) {
        try {
            if (jsonString == null) {
                return null;
            }

            // First, read the JSON into a tree to check the type
            var jsonNode = objectMapper.readTree(jsonString);
            var typeNode = jsonNode.get("type");

            if (typeNode == null) {
                throw new CouponConverterException("Missing coupon type in JSON");
            }

            String type = typeNode.asText();

            // Based on the type, deserialize into the appropriate class
            return switch (type.toUpperCase()) {
                case "CART_WISE" -> objectMapper.readValue(jsonString, CartWiseCouponDetails.class);
                case "PRODUCT_WISE" -> objectMapper.readValue(jsonString, ProductWiseCouponDetails.class);
                case "BXGY" -> objectMapper.readValue(jsonString, BxGyCouponDetails.class);
                default -> throw new CouponConverterException("Unknown coupon type: " + type);
            };
        } catch (JsonProcessingException e) {
            throw new CouponConverterException("Error converting JSON to coupon details", e);
        }
    }
}

