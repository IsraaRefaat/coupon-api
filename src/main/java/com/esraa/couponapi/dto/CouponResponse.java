package com.esraa.couponapi.dto;

import com.esraa.couponapi.entity.DiscountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponResponse {

    private Long id;
    private String code;
    private Integer totalUsages;
    private Integer remainingUsages;
    private LocalDateTime expiryDate;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}