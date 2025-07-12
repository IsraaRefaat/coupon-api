package com.esraa.couponapi.repository;

import com.esraa.couponapi.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCode(String code);

    @Query("SELECT c FROM Coupon c WHERE c.isActive = true AND c.expiryDate > :now AND c.remainingUsages > 0")
    List<Coupon> findValidCoupons(LocalDateTime now);

    @Query("SELECT c FROM Coupon c WHERE c.expiryDate < :now")
    List<Coupon> findExpiredCoupons(LocalDateTime now);

    boolean existsByCode(String code);
}