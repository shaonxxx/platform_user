package com.woniu.woniuticket.platform_user.service;


import com.woniu.woniuticket.platform_user.pojo.Coupon;

public interface CouponService {
    Coupon findCouponByUserId(Integer userId);

    int countCouponByUserId(Integer userId);

    int addCoupon(Coupon coupon);

    int modifyCoupon(Coupon coupon);

    Coupon couponOut(Integer userId);
}
