package com.esraa.couponapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUPON")
    @SequenceGenerator(name = "SEQ_COUPON", sequenceName = "SEQ_COUPONS", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @NotNull
    @Column(name = "total_usages", nullable = false)
    private Integer totalUsages;

    @NotNull
    @Column(name = "remaining_usages", nullable = false)
    private Integer remainingUsages;

    @NotNull
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @NotNull
    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean hasRemainingUsages() {
        return remainingUsages > 0;
    }

    public boolean isValid() {
        return isActive && !isExpired() && hasRemainingUsages();
    }
}