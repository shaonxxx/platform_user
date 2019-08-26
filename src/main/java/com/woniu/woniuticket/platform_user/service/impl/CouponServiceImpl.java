package com.woniu.woniuticket.platform_user.service.impl;


import com.woniu.woniuticket.platform_user.mapper.CouponDao;
import com.woniu.woniuticket.platform_user.pojo.Coupon;
import com.woniu.woniuticket.platform_user.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponDao couponDao;

    @Override
    public List<Coupon> findCouponByUserId(Integer userId) {
        List<Coupon> coupon = couponDao.selectCouponByUserId(userId);
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
    public List<Coupon> couponOut(Integer userId) {
        //查找已过期优惠券
        List<Coupon> outCouponList = couponDao.selectCouponOut(userId);
        for (Coupon outCoupon : outCouponList) {
            if(outCoupon!=null) {
                //0表示优惠券可以使用
                //1表示优惠券已使用
                //2表示优惠券已过期
                //3表示优惠券已删除

                //修改优惠券状态为过期
                outCoupon.setState(2);
                couponDao.updateByPrimaryKey(outCoupon);
            }
        }
        return outCouponList;
    }

    @Override
    public int createCoupon(Integer userId) {
        Coupon coupon=new Coupon();
        coupon.setUserId(userId);
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,7);
        coupon.setActiveTime(calendar.getTime());
        coupon.setAmount((float) 5);
        coupon.setState(0);
        return couponDao.insert(coupon);
    }

    @Override
    public Coupon findCouponByCouponId(Integer couponId) {
        return couponDao.selectByPrimaryKey(couponId);
    }
}
