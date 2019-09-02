package com.woniu.woniuticket.platform_user.controller;


import com.github.pagehelper.PageInfo;
import com.woniu.woniuticket.platform_user.exception.CouponException;
import com.woniu.woniuticket.platform_user.pojo.Coupon;
import com.woniu.woniuticket.platform_user.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@Api("用户优惠券管理")
public class CouponController {
    @Autowired
    CouponService couponService;
    @Autowired
    RedisTemplate redisTemplate;
    /*
    * 优惠券信息
    * @param couponId
    * @return 返回优惠券对象
    * */
    @ApiOperation(value = "获取优惠券信息",notes = "根据url的couponId获取优惠券")
    @GetMapping("/coupon/{couponId}")
    @ResponseBody
    public Coupon findCouponByCouponId(@PathVariable("couponId") Integer couponId){
        Coupon coupon = couponService.findCouponByCouponId(couponId);
        return coupon;
    }

    /*
    * 用户拥有的优惠券集合
    * @param userId
    * @return 返回优惠券list集合
    * */
    @ApiOperation(value = "用户未删除的优惠券列表",notes = "")
    @GetMapping("/couponList/{userId}")
    @ResponseBody
    public PageInfo<Coupon> findCouponByUserId(@PathVariable("userId")Integer userId,
                                           @RequestParam(value = "pageSize",defaultValue = "10",required = false)Integer pageSize,
                                           @RequestParam(value = "currentPage",defaultValue = "1",required = false)Integer currentPage){
        List<Coupon> couponList = couponService.findCouponByUserId(pageSize,currentPage,userId);
        redisTemplate.opsForList().rightPushAll("couponList",couponList);
        PageInfo<Coupon> pageInfo=new PageInfo<>(couponList);
        return pageInfo;
    }


    /*
    * 删除过期优惠券
    * @param couponId
    * @return 返回结果集合
    * */
    @ApiOperation(value = "修改用户优惠券信息，完成假删",notes = "根据url的couponId修改")
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
    * @param userId
    * @return 返回结果集合
    * */
    @ApiOperation(value = "更改过期优惠券状态",notes = "根据url的userId找到过期优惠券，进行修改")
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
    * 未过期优惠券数量展示
    * @param userId
    * @return 返回优惠券数量
    * */
    @ApiOperation(value = "可使用优惠券计数",notes = "根据url的userId找到未过期优惠券，数量统计")
    @GetMapping("/couponCount/{userId}")
    @ResponseBody
    public int countCoupon(@PathVariable("userId")Integer userId){
        int count = couponService.countCouponByUserId(userId);
        return  count;
    }


    /*
    * 消费使用优惠券
    * @param couponId
    * @return 返回结果集合
    * */
    @ApiOperation(value = "消费优惠券，修改优惠券状态",notes = "根据url的couponId找到可使用优惠券，进行修改")
    @PutMapping("/reduceCoupon/{couponId}")
    public Map reduceCoupon(@PathVariable("couponId") Integer couponId){
        Map result=new HashMap();
        Coupon coupon = couponService.findCouponByCouponId(couponId);
        coupon.setState(1);
        try {
            couponService.modifyCoupon(coupon);
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
     * @param userId
     * @return 返回结果集合
     * */
    @ApiOperation(value = "分享好友，创建一张优惠券给用户",notes = "根据url的userId，为其新建一张优惠券")
    @PutMapping("/addCoupon/{userId}")
    public Map addCoupon(@PathVariable("userId")Integer userId){
        Map result=new HashMap();
        try{
            couponService.createCoupon(userId);
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
     * @param coupon
     * @return 返回结果集合
     * */

/*    @PostMapping("/coupon")
    public Map addCoupon(@Requestbody Coupon coupon){
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
    }*/


    /*
     * 修改优惠券信息(后台管理）
     * @param coupon
     * @return 返回结果集合
     * */

/*    @PutMapping("/coupon")
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
    }*/
}
