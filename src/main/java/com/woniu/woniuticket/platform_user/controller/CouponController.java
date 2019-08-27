package com.woniu.woniuticket.platform_user.controller;


import com.woniu.woniuticket.platform_user.exception.CouponException;
import com.woniu.woniuticket.platform_user.pojo.Coupon;
import com.woniu.woniuticket.platform_user.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.util.resources.ar.CalendarData_ar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class CouponController {
    @Autowired
    CouponService couponService;


    /*
    * 查看优惠券信息
    *
    * */

    @GetMapping("/coupon/{userId}")
    @ResponseBody
    public List<Coupon> findCouponByUserId(@PathVariable("userId")Integer userId){
        List<Coupon> couponList = couponService.findCouponByUserId(userId);
        return couponList;
    }


    /*
    * 删除过期优惠券
    *
    * */

    @DeleteMapping("/coupon/{couponId}")
    public Map deleteCoupon(@PathVariable("couponId")Integer couponId){
        Map result=new HashMap();
        try {
//            List<Coupon> outCouponList = couponService.couponOut(userId);
//            for (Coupon outCoupon : outCouponList) {
//                //设置为被删除状态
//                outCoupon.setState(3);
            Coupon coupon = couponService.findCouponByCouponId(couponId);
            coupon.setState(3);
            int code = couponService.modifyCoupon(coupon);
            result.put("code", 0);
            result.put("msg", "删除成功");
        }catch (CouponException e){
            result.put("code",500);
            result.put("msg","删除失败");
        }
        return result;
    }


    /*
    * 优惠券过期
    *
    * */

    @GetMapping("/outCoupon/{userId}")
    public Map timeoutCoupon(@PathVariable("userId") Integer userId){
        Map result=new HashMap();
        try {
            List<Coupon> outCouponList = couponService.couponOut(userId);
            if(outCouponList!=null){
                result.put("msg","优惠券已失效");
                result.put("code",500);
            }else{
                result.put("code",0);
                result.put("msg","无失效优惠券");
            }
        } catch (CouponException e) {
            e.printStackTrace();
            result.put("msg",e.getMessage());
            result.put("code",500);
        }
        return result;
    }


    /*
    * 优惠券数量展示
    *
    * */

    @GetMapping("/couponCount/{userId}")
    @ResponseBody
    public int countCoupon(@PathVariable("userId")Integer userId){
        int count = couponService.countCouponByUserId(userId);
        return  count;
    }


    /*
    * 消费使用优惠券
    *
    * */

    @PutMapping("/reduceCoupon/{userId}")
    public Map reduceCoupon(@PathVariable("userId") Integer couponId){
        Map result=new HashMap();
        Coupon coupon = couponService.findCouponByCouponId(couponId);
        coupon.setState(1);
        try {
            int code = couponService.modifyCoupon(coupon);
            result.put("code", 0);
            result.put("msg", "使用成功，优惠券减少");
        } catch (CouponException e) {
            result.put("code", 500);
            result.put("msg", "使用失败");
         }
            return result;
    }


    /*
     * 分享好友，创造优惠券
     *
     * */

    @PutMapping("/addCoupon/{userId}")
    public Map addCoupon(@PathVariable("userId")Integer userId){
        Map result=new HashMap();
        try{
            int code = couponService.createCoupon(userId);
            result.put("code", 0);
            result.put("msg", "分享成功，优惠券增加");
        } catch (CouponException e){
            e.printStackTrace();
            result.put("msg","分享失败");
            result.put("code",500);
        }
             return result;
    }



    /*
     * 添加优惠券(后台管理)
     *
     * */

    @PostMapping("/coupon")
    public Map addCoupon(Coupon coupon){
        Map result=new HashMap();
        try {
            int code = couponService.addCoupon(coupon);
            result.put("code",0);
            result.put("msg","添加优惠券成功");
        } catch (CouponException e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg","添加优惠券失败");
        }
        return  result;
    }


    /*
     * 修改优惠券信息(后台管理）
     *
     * */

    @PutMapping("/coupon")
    public Map updateCoupon(Coupon coupon){
        Map result=new HashMap();
        try {
            int code = couponService.modifyCoupon(coupon);
            result.put("code",0);
            result.put("msg","修改成功");
        } catch (CouponException e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg","修改失败");
        }
        return  result;
    }
}
