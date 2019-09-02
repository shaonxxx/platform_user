package com.woniu.woniuticket.platform_user;

import com.woniu.woniuticket.platform_user.controller.CouponController;
import com.woniu.woniuticket.platform_user.mapper.CouponDao;
import com.woniu.woniuticket.platform_user.pojo.Coupon;
import com.woniu.woniuticket.platform_user.service.CouponService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CouponTest {
    @Autowired
    CouponDao couponDao;
    @Autowired
    CouponService couponService;
    @Autowired
    CouponController couponController;

    @Test
    public void testShareCoupon1(){
        Map map = couponController.addCoupon(1);
        System.out.println(map);
    }

    @Test
    public void testShareCoupon2(){
        Map map = couponController.addCoupon(2);
        System.out.println(map);
    }

    @Test
    public void testdate(){
        Calendar calendar=Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();

        calendar.add(Calendar.DATE,7);
        System.out.println(calendar.getTime());

        Coupon coupon2 = couponDao.selectByPrimaryKey(2);
        calendar1.setTime(coupon2.getActiveTime());
        System.out.println(calendar1.getTime());
        System.out.println(calendar.getTime());
        System.out.println(calendar1.getTime()==calendar.getTime());
    }




    @Test
    public  void testCount(){
        int i = couponDao.countCouponByUserId(1);
        System.out.println(i);
    }

    @Test
    public void testCountOut(){
        List<Coupon> coupon = couponDao.selectCouponOut(1);
        System.out.println(coupon);
    }

    @Test
    public void testUpdateCoupon(){
        List<Coupon> coupon = couponService.couponOut(1);
    }

    @Test
    public void testCreateCoupon(){
        couponService.createCoupon(1);
    }
}
