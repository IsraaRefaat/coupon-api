package com.esraa.couponapi.dto;

import com.esraa.couponapi.entity.DiscountType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponCreateRequest {

    @NotBlank(message = "Coupon code is required")
    @Size(min = 1, max = 100, message = "Coupon code must be between 1 and 100 characters")
    private String code;

    @NotNull(message = "Total usages is required")
    @Min(value = 1, message = "Total usages must be at least 1")
    private Integer totalUsages;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDateTime expiryDate;

    @NotNull(message = "Discount type is required")
    private DiscountType discountType;

    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.01", message = "Discount value must be greater than 0")
    private BigDecimal discountValue;
}