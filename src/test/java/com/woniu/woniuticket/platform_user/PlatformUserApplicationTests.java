package com.woniu.woniuticket.platform_user;

import com.woniu.woniuticket.platform_user.mapper.UserDao;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.UserService;
import com.woniu.woniuticket.platform_user.mapper.CouponDao;
import com.woniu.woniuticket.platform_user.pojo.Coupon;
import com.woniu.woniuticket.platform_user.service.CouponService;

import com.woniu.woniuticket.platform_user.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;


import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformUserApplicationTests {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
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
    public void testUserLogin(){
        User findUser = userService.findUserByName("qweqwe");
        System.out.println(findUser);
    }
    @Test
    public void contextLoads() {
        User user = new User();
        user.setUserName("qqq");
        user.setEmail("qqq");
        user.setMobile("qqq");
        System.out.println(user);

        User findName = userDao.selectUserByName(user.getUserName());
        User findEmail = userDao.selectUserByEmail(user.getEmail());
        User findMobile = userDao.selectUserByMobile(user.getMobile());

        System.out.println("1."+findName);
        System.out.println("2."+findEmail);
        System.out.println("3."+findMobile);
    }
    @Test
    public void login() {
        String loginName = "qweqweq";
        String password = DigestUtils.md5DigestAsHex("qweqweee".getBytes());

        User findUser = userService.findUserByName(loginName);
        if (findUser == null) {
            System.out.println("不是用户名");
            String emailE = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
            if (loginName.matches(emailE)) {
                System.out.println("是邮箱");
                findUser = userService.findUserByEmail(loginName);
            } else {
                hashCode(); //避免横线看着不爽随便加了点料
                System.out.println("是手机");
                findUser = userService.findUserByMobile(loginName);
            }
        }
        if (findUser == null) {
            System.out.println("用户名或密码不正确");
            return;
        } else {
            if (!findUser.getPassword().equals(password)) {
                System.out.println("密码不正确");
                return;
            }
            System.out.println("*************");
            return;
        }
    }
    public void testListUser(){
        UserVo userVo=new UserVo();
        userVo.setUserName("aaa");
        List<User> list = userDao.selectUserByPage(5, 1,userVo );
        for (User lists : list) {
            System.out.println(lists.getUserName());
        }
    }

//    @Test
//    public void testFindCoupon(){
//        Coupon coupon = couponDao.selectCouponByUserId(1);
//        System.out.println(coupon.getNum());
//    }
//
//    @Test
//    public  void testCount(){
//        int i = couponDao.countCouponByUserId(1);
//        System.out.println(i);
//    }
//
//    @Test
//    public void testCountOut(){
//        Coupon coupon = couponDao.selectCouponOut(1);
//        System.out.println(coupon);
//    }
//
//    @Test
//    public void testUpdateCoupon(){
//        Coupon coupon = couponService.couponOut(1);
//    }

}
