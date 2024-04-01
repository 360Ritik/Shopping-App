package com.Shopping.App.service;

import com.Shopping.App.schema.Coupon;
import com.Shopping.App.service.repo.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

@Service
public class CouponService {


    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Map<String, Integer> fetchCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream().collect(Collectors.toMap(Coupon::getCoupon, Coupon::getDiscount));
    }

    public Coupon findByCouponCode(String couponCode) {
        return couponRepository.findByCoupon(couponCode);
    }

    public Coupon saveCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
