package com.Shopping.App.service.repo;

import com.Shopping.App.schema.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon findByCoupon(String code);
}
