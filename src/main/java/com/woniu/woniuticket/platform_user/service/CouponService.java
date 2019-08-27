package com.woniu.woniuticket.platform_user.service;


import com.woniu.woniuticket.platform_user.pojo.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> findCouponByUserId(Integer userId);

    int countCouponByUserId(Integer userId);

    int addCoupon(Coupon coupon);

    int modifyCoupon(Coupon coupon);

    List<Coupon> couponOut(Integer userId);

    int createCoupon(Integer userId);

    Coupon findCouponByCouponId(Integer couponId);
}
