package com.esraa.couponapi.repository;

import com.esraa.couponapi.entity.CouponConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponConsumptionRepository extends JpaRepository<CouponConsumption, Long> {

    List<CouponConsumption> findByCouponId(Long couponId);

    List<CouponConsumption> findByCustomerId(String customerId);

    List<CouponConsumption> findByOrderId(String orderId);

    @Query("SELECT cc FROM CouponConsumption cc WHERE cc.coupon.id = :couponId ORDER BY cc.consumedAt DESC")
    List<CouponConsumption> findByCouponIdOrderByConsumedAtDesc(@Param("couponId") Long couponId);

    @Query("SELECT cc FROM CouponConsumption cc WHERE cc.customerId = :customerId ORDER BY cc.consumedAt DESC")
    List<CouponConsumption> findByCustomerIdOrderByConsumedAtDesc(@Param("customerId") String customerId);
}