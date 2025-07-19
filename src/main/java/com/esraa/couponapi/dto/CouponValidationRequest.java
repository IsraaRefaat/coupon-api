package com.esraa.couponapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponValidationRequest {
    
    @NotBlank(message = "Coupon code is required")
    private String couponCode;
    
    @NotNull(message = "Order total is required")
    @Positive(message = "Order total must be positive")
    private BigDecimal orderTotal;
    
    @NotBlank(message = "Customer ID is required")
    private String customerId;
}