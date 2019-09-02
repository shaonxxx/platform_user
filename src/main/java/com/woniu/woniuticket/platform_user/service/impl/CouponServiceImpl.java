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
    /*
    * 根据用户id分页查询用户未删除的优惠券
    * @param userId
    * @return
    * */
    @Override
    public List<Coupon> findCouponByUserId(Integer pageSize,Integer currentPage,Integer userId) {
        List<Coupon> coupon = couponDao.selectCouponByUserId(pageSize,currentPage,userId);
        return  coupon;
    }

    /*
    * 根据用户id计数当前可使用的优惠券
    * @param userId
    * @return
    * */
    @Override
    public int countCouponByUserId(Integer userId) {
        return couponDao.countCouponByUserId(userId);
    }

    /*
    * 优惠券添加
    * @param coupon
    * @return
    * */
    @Override
    public int addCoupon(Coupon coupon) {
        return couponDao.insert(coupon);
    }

    /*
    * 修改优惠券信息
    * @param coupon
    * @return
    * */
    @Override
    public int modifyCoupon(Coupon coupon) {
        return couponDao.updateByPrimaryKey(coupon);
    }

    /*
    * 根据用户id查找已过期优惠券，修改其状态
    * @param userId
    * @return
    * */
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

    /*
    * 创造用户分享所得优惠券
    * @param userId
    * @return
    * */
    @Override
    public int createCoupon(Integer userId) {
        Coupon coupon=new Coupon();
        coupon.setUserId(userId);
        Calendar calendar=Calendar.getInstance();
        //优惠券有效时间7天
        calendar.add(Calendar.DATE,7);
        coupon.setActiveTime(calendar.getTime());
        //优惠券金额5
        coupon.setAmount((float) 5);
        //状态0表示未使用
        coupon.setState(0);
        return couponDao.insert(coupon);
    }

    /*
    * 根据优惠券id查找优惠券
    * @param couponId
    * @return
    * */
    @Override
    public Coupon findCouponByCouponId(Integer couponId) {
        return couponDao.selectByPrimaryKey(couponId);
    }
}
