package com.esraa.couponapi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponValidationResponse {
    
    private boolean valid;
    private String couponCode;
    private BigDecimal discountAmount;
    private String discountType;
    private String message;
    
    public static CouponValidationResponse valid(String couponCode, BigDecimal discountAmount, String discountType) {
        CouponValidationResponse response = new CouponValidationResponse();
        response.setValid(true);
        response.setCouponCode(couponCode);
        response.setDiscountAmount(discountAmount);
        response.setDiscountType(discountType);
        response.setMessage("Coupon is valid");
        return response;
    }
    
    public static CouponValidationResponse invalid(String couponCode, String message) {
        CouponValidationResponse response = new CouponValidationResponse();
        response.setValid(false);
        response.setCouponCode(couponCode);
        response.setDiscountAmount(BigDecimal.ZERO);
        response.setMessage(message);
        return response;
    }
}