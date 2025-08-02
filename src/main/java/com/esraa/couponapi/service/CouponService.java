package com.esraa.couponapi.service;

import com.esraa.couponapi.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CouponService {
     CouponResponse createCoupon(CouponCreateRequest request);

     CouponConsumptionResponse consumeCoupon(CouponConsumeRequest request);

     List<CouponResponse> getAllCoupons();

     List<CouponResponse> getValidCoupons();

     CouponResponse getCouponByCode(String code);

     List<CouponConsumptionResponse> getCouponConsumptions(Long couponId);

     List<CouponConsumptionResponse> getCustomerConsumptions(String customerId);

     CouponValidationResponse validateCoupon(CouponValidationRequest request);


}
