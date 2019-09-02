package com.woniu.woniuticket.platform_user.mapper;


import com.woniu.woniuticket.platform_user.pojo.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponDao {
    int deleteByPrimaryKey(Integer couponId);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer couponId);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    List<Coupon> selectCouponByUserId(@Param("pageSize") Integer pageSize, @Param("currentPage") Integer currentPage,@Param("userId")Integer userId);

    int countCouponByUserId(Integer userId);

    List<Coupon> selectCouponOut(Integer userId);
}