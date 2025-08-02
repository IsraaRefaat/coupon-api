package com.esraa.couponapi.service.impl;

import com.esraa.couponapi.dto.*;
import com.esraa.couponapi.entity.Coupon;
import com.esraa.couponapi.entity.CouponConsumption;
import com.esraa.couponapi.entity.DiscountType;
import com.esraa.couponapi.exception.CouponException;
import com.esraa.couponapi.repository.CouponConsumptionRepository;
import com.esraa.couponapi.repository.CouponRepository;
import com.esraa.couponapi.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponConsumptionRepository consumptionRepository;

    @Transactional
    public CouponResponse createCoupon(CouponCreateRequest request) {
        log.info("Creating coupon with code: {}", request.getCode());
        
        if (couponRepository.existsByCode(request.getCode())) {
            throw new CouponException("Coupon with code '" + request.getCode() + "' already exists");
        }

        Coupon coupon = new Coupon();
        coupon.setCode(request.getCode());
        coupon.setTotalUsages(request.getTotalUsages());
        coupon.setRemainingUsages(request.getTotalUsages());
        coupon.setExpiryDate(request.getExpiryDate());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setDiscountValue(request.getDiscountValue());
        coupon.setIsActive(true);

        Coupon savedCoupon = couponRepository.save(coupon);
        log.info("Created coupon with ID: {}", savedCoupon.getId());
        
        return convertToCouponResponse(savedCoupon);
    }

    @Transactional
    public CouponConsumptionResponse consumeCoupon(CouponConsumeRequest request) {
        log.info("Consuming coupon with code: {} for customer: {}", request.getCouponCode(), request.getCustomerId());
        
        Coupon coupon = couponRepository.findByCode(request.getCouponCode())
                .orElseThrow(() -> new CouponException("Coupon not found with code: " + request.getCouponCode()));

        if (!coupon.isValid()) {
            String reason = getInvalidReason(coupon);
            throw new CouponException("Coupon is not valid: " + reason);
        }

        BigDecimal discountApplied = calculateDiscount(coupon);

        coupon.setRemainingUsages(coupon.getRemainingUsages() - 1);
        couponRepository.save(coupon);

        CouponConsumption consumption = new CouponConsumption();
        consumption.setCoupon(coupon);
        consumption.setCustomerId(request.getCustomerId());
        consumption.setOrderId(request.getOrderId());
        consumption.setDiscountApplied(discountApplied);

        CouponConsumption savedConsumption = consumptionRepository.save(consumption);
        log.info("Consumed coupon with ID: {} for order: {}", coupon.getId(), request.getOrderId());
        
        return convertToConsumptionResponse(savedConsumption);
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(this::convertToCouponResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> getValidCoupons() {
        return couponRepository.findValidCoupons(LocalDateTime.now()).stream()
                .map(this::convertToCouponResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CouponResponse getCouponByCode(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new CouponException("Coupon not found with code: " + code));
        return convertToCouponResponse(coupon);
    }

    @Transactional(readOnly = true)
    public List<CouponConsumptionResponse> getCouponConsumptions(Long couponId) {
        return consumptionRepository.findByCouponIdOrderByConsumedAtDesc(couponId).stream()
                .map(this::convertToConsumptionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CouponConsumptionResponse> getCustomerConsumptions(String customerId) {
        return consumptionRepository.findByCustomerIdOrderByConsumedAtDesc(customerId).stream()
                .map(this::convertToConsumptionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CouponValidationResponse validateCoupon(CouponValidationRequest request) {
        log.info("Validating coupon: {} for order total: {}", request.getCouponCode(), request.getOrderTotal());
        
        try {
            Coupon coupon = couponRepository.findByCode(request.getCouponCode())
                    .orElseThrow(() -> new CouponException("Coupon not found with code: " + request.getCouponCode()));

            if (!coupon.isValid()) {
                String reason = getInvalidReason(coupon);
                return CouponValidationResponse.invalid(request.getCouponCode(), reason);
            }

            BigDecimal discountAmount = calculateCouponDiscount(coupon, request.getOrderTotal());
            return CouponValidationResponse.valid(
                    request.getCouponCode(), 
                    discountAmount, 
                    coupon.getDiscountType().toString()
            );
        } catch (CouponException e) {
            return CouponValidationResponse.invalid(request.getCouponCode(), e.getMessage());
        }
    }

    private BigDecimal calculateDiscount(Coupon coupon) {
        return coupon.getDiscountValue();
    }

    private BigDecimal calculateCouponDiscount(Coupon coupon, BigDecimal orderTotal) {
        if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
            return orderTotal.multiply(coupon.getDiscountValue()).divide(BigDecimal.valueOf(100));
        } else {
            return coupon.getDiscountValue().min(orderTotal);
        }
    }

    private String getInvalidReason(Coupon coupon) {
        if (!coupon.getIsActive()) {
            return "Coupon is inactive";
        }
        if (coupon.isExpired()) {
            return "Coupon has expired";
        }
        if (!coupon.hasRemainingUsages()) {
            return "Coupon has no remaining usages";
        }
        return "Unknown reason";
    }

    private CouponResponse convertToCouponResponse(Coupon coupon) {
        CouponResponse response = new CouponResponse();
        response.setId(coupon.getId());
        response.setCode(coupon.getCode());
        response.setTotalUsages(coupon.getTotalUsages());
        response.setRemainingUsages(coupon.getRemainingUsages());
        response.setExpiryDate(coupon.getExpiryDate());
        response.setDiscountType(coupon.getDiscountType());
        response.setDiscountValue(coupon.getDiscountValue());
        response.setIsActive(coupon.getIsActive());
        response.setCreatedAt(coupon.getCreatedAt());
        response.setUpdatedAt(coupon.getUpdatedAt());
        return response;
    }

    private CouponConsumptionResponse convertToConsumptionResponse(CouponConsumption consumption) {
        CouponConsumptionResponse response = new CouponConsumptionResponse();
        response.setId(consumption.getId());
        response.setCouponCode(consumption.getCoupon().getCode());
        response.setCustomerId(consumption.getCustomerId());
        response.setOrderId(consumption.getOrderId());
        response.setDiscountApplied(consumption.getDiscountApplied());
        response.setConsumedAt(consumption.getConsumedAt());
        return response;
    }
}