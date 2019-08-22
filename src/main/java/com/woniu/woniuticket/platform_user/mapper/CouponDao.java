package com.woniu.woniuticket.platform_user.mapper;


import com.woniu.woniuticket.platform_user.pojo.Coupon;

public interface CouponDao {
    int deleteByPrimaryKey(Integer couponId);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer couponId);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    Coupon selectCouponByUserId(Integer userId);

    int countCouponByUserId(Integer userId);

    Coupon selectCouponOut(Integer userId);
}