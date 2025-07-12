package com.esraa.couponapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CouponConsumeRequest {

    @NotBlank(message = "Coupon code is required")
    @Size(min = 1, max = 100, message = "Coupon code must be between 1 and 100 characters")
    private String couponCode;

    @NotBlank(message = "Customer ID is required")
    @Size(min = 1, max = 100, message = "Customer ID must be between 1 and 100 characters")
    private String customerId;

    @NotBlank(message = "Order ID is required")
    @Size(min = 1, max = 100, message = "Order ID must be between 1 and 100 characters")
    private String orderId;
}