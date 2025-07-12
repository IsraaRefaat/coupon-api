package com.esraa.couponapi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponConsumptionResponse {

    private Long id;
    private String couponCode;
    private String customerId;
    private String orderId;
    private BigDecimal discountApplied;
    private LocalDateTime consumedAt;
}