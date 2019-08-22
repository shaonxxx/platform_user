package com.woniu.woniuticket.platform_user.controller;


import com.woniu.woniuticket.platform_user.exception.CouponException;
import com.woniu.woniuticket.platform_user.pojo.Coupon;
import com.woniu.woniuticket.platform_user.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public Coupon findCouponByUserId(@PathVariable("userId")Integer userId){
        Coupon coupon = couponService.findCouponByUserId(userId);
        return coupon;
    }


    /*
    * 删除过期优惠券
    *
    * */

    @DeleteMapping("/coupon/{userId}")
    public Map deleteCoupon(@PathVariable("userId")Integer userId){
        Map result=new HashMap();
        try {
            Coupon outCoupon = couponService.couponOut(userId);
            outCoupon.setState(2);
            int code = couponService.modifyCoupon(outCoupon);
            if (code == 0) {
                result.put("code", 0);
                result.put("msg", "删除成功");
            } else {
                result.put("code", 500);
                result.put("msg", "删除失败");
            }
        }catch (CouponException e){
            result.put("code",500);
            result.put("msg",e.getMessage());
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
            Coupon outCoupon = couponService.couponOut(userId);
            if(outCoupon!=null){
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
    public Map reduceCoupon(Integer userId){
        Map result=new HashMap();
        Coupon coupon = couponService.findCouponByUserId(userId);
        Integer num = coupon.getNum();
        num=num-1;
        coupon.setNum(num);
        try {
            int code = couponService.modifyCoupon(coupon);
            if (code == 0) {
                result.put("code", 0);
                result.put("msg", "使用成功，优惠券减少");
            } else {
                result.put("code", 500);
                result.put("msg", "使用失败");
            }
        }catch (CouponException e){
            result.put("code",500);
            result.put("msg",e.getMessage());
        }
        return result;
    }


    /*
     * 分享好友，优惠券增加
     *
     * */

    @PutMapping("/addCoupon/{userId}")
    public Map addCoupon(Integer userId){
        Map result=new HashMap();
        Coupon coupon = couponService.findCouponByUserId(userId);
        Integer num = coupon.getNum();
        num=num+1;
        coupon.setNum(num);
        try {
            int code = couponService.modifyCoupon(coupon);
            if (code == 0) {
                result.put("code", 0);
                result.put("msg", "分享成功，优惠券增加");
            } else {
                result.put("code", 500);
                result.put("msg", "分享失败");
            }
        }catch (CouponException e){
            result.put("code",500);
            result.put("msg",e.getMessage());
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
            if(code==0){
                result.put("code",0);
                result.put("msg","添加优惠券成功");
            }else{
                result.put("code",500);
                result.put("msg","添加优惠券失败");
            }
        } catch (CouponException e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg",e.getMessage());
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
            if(code==0){
                result.put("code",0);
                result.put("msg","修改成功");
            }else{
                result.put("code",500);
                result.put("msg","修改失败");
            }
        } catch (CouponException e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg",e.getMessage());
        }
        return  result;
    }
}
