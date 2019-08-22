package com.woniu.woniuticket.platform_user;

import com.woniu.woniuticket.platform_user.mapper.CouponDao;
import com.woniu.woniuticket.platform_user.mapper.UserDao;
import com.woniu.woniuticket.platform_user.pojo.Coupon;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.CouponService;
import com.woniu.woniuticket.platform_user.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformUserApplicationTests {
    @Autowired
    UserDao userDao;
    @Autowired
    CouponDao couponDao;
    @Autowired
    CouponService couponService;
    @Test
    public void testFindUser() {
        User user = userDao.selectUserByUserName("aaa");
        System.out.println(user.getUserName());
    }
    @Test
    public void testListUser(){
        UserVo userVo=new UserVo();
        userVo.setUserName("aaa");
        List<User> list = userDao.selectUserByPage(5, 1,userVo );
        for (User lists : list) {
            System.out.println(lists.getUserName());
        }
    }

    @Test
    public void testFindCoupon(){
        Coupon coupon = couponDao.selectCouponByUserId(1);
        System.out.println(coupon.getNum());
    }

    @Test
    public  void testCount(){
        int i = couponDao.countCouponByUserId(1);
        System.out.println(i);
    }

    @Test
    public void testCountOut(){
        Coupon coupon = couponDao.selectCouponOut(1);
        System.out.println(coupon);
    }

    @Test
    public void testUpdateCoupon(){
        Coupon coupon = couponService.couponOut(1);
    }

}
