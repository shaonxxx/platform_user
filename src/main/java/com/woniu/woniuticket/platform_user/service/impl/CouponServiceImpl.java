package com.woniu.woniuticket.platform_user.service.impl;


import com.woniu.woniuticket.platform_user.mapper.CouponDao;
import com.woniu.woniuticket.platform_user.pojo.Coupon;
import com.woniu.woniuticket.platform_user.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponDao couponDao;

    @Override
    public Coupon findCouponByUserId(Integer userId) {
        Coupon coupon = couponDao.selectCouponByUserId(userId);
        Date date=new Date();
        return  coupon;
    }

    @Override
    public int countCouponByUserId(Integer userId) {
        return couponDao.countCouponByUserId(userId);
    }

    @Override
    public int addCoupon(Coupon coupon) {
        return couponDao.insert(coupon);
    }

    @Override
    public int modifyCoupon(Coupon coupon) {
        return couponDao.updateByPrimaryKey(coupon);
    }

    @Override
    public Coupon couponOut(Integer userId) {
        //查找已过期优惠券
        Coupon outCoupon = couponDao.selectCouponOut(userId);
        if(outCoupon!=null) {
            //0表示优惠券可以使用
            //1表示优惠券已过期
            //2表示优惠券已被删除

            //修改优惠券状态为过期
            outCoupon.setState(1);
            couponDao.updateByPrimaryKey(outCoupon);
        }
        return outCoupon;
    }
}
