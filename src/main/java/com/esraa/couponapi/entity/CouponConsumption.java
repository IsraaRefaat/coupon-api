package com.esraa.couponapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_consumptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUPON_CONSUMPTIONS")
    @SequenceGenerator(name = "SEQ_COUPON_CONSUMPTIONS", sequenceName = "SEQ_COUPON_CONSUMPTIONS", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @NotNull
    @Column(name = "discount_applied", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountApplied;

    @Column(name = "consumed_at", nullable = false, updatable = false)
    private LocalDateTime consumedAt;

    @PrePersist
    protected void onCreate() {
        consumedAt = LocalDateTime.now();
    }
}