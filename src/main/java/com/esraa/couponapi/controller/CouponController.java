package com.esraa.couponapi.controller;

import com.esraa.couponapi.dto.*;
import com.esraa.couponapi.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(@Valid @RequestBody CouponCreateRequest request) {
        log.info("Creating coupon with code: {}", request.getCode());
        CouponResponse response = couponService.createCoupon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/consume")
    public ResponseEntity<CouponConsumptionResponse> consumeCoupon(@Valid @RequestBody CouponConsumeRequest request) {
        log.info("Consuming coupon with code: {} for customer: {}", request.getCouponCode(), request.getCustomerId());
        CouponConsumptionResponse response = couponService.consumeCoupon(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CouponResponse>> getAllCoupons() {
        log.info("Getting all coupons");
        List<CouponResponse> response = couponService.getAllCoupons();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/valid")
    public ResponseEntity<List<CouponResponse>> getValidCoupons() {
        log.info("Getting valid coupons");
        List<CouponResponse> response = couponService.getValidCoupons();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<CouponResponse> getCouponByCode(@PathVariable String code) {
        log.info("Getting coupon by code: {}", code);
        CouponResponse response = couponService.getCouponByCode(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{couponId}/consumptions")
    public ResponseEntity<List<CouponConsumptionResponse>> getCouponConsumptions(@PathVariable Long couponId) {
        log.info("Getting consumptions for coupon ID: {}", couponId);
        List<CouponConsumptionResponse> response = couponService.getCouponConsumptions(couponId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers/{customerId}/consumptions")
    public ResponseEntity<List<CouponConsumptionResponse>> getCustomerConsumptions(@PathVariable String customerId) {
        log.info("Getting consumptions for customer ID: {}", customerId);
        List<CouponConsumptionResponse> response = couponService.getCustomerConsumptions(customerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<CouponValidationResponse> validateCoupon(@Valid @RequestBody CouponValidationRequest request) {
        log.info("Validating coupon: {} for customer: {}", request.getCouponCode(), request.getCustomerId());
        CouponValidationResponse response = couponService.validateCoupon(request);
        return ResponseEntity.ok(response);
    }
}