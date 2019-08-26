package com.woniu.woniuticket.platform_user.mapper;


import com.woniu.woniuticket.platform_user.pojo.Coupon;

import java.util.List;

public interface CouponDao {
    int deleteByPrimaryKey(Integer couponId);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer couponId);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    List<Coupon> selectCouponByUserId(Integer userId);

    int countCouponByUserId(Integer userId);

    List<Coupon> selectCouponOut(Integer userId);
}