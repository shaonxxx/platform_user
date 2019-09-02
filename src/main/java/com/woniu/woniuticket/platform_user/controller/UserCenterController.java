package com.woniu.woniuticket.platform_user.controller;


import com.github.pagehelper.PageInfo;
import com.woniu.woniuticket.platform_user.constant.UserConstant;
import com.woniu.woniuticket.platform_user.exception.UserCenterException;
import com.woniu.woniuticket.platform_user.exception.UserException;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.pojo.WalletOrder;
import com.woniu.woniuticket.platform_user.service.CouponService;
import com.woniu.woniuticket.platform_user.service.UserService;

import com.woniu.woniuticket.platform_user.service.WalletOrderService;
import com.woniu.woniuticket.platform_user.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.UsernamePasswordToken;

import org.apache.tomcat.util.json.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@CrossOrigin
@Api("用户信息管理")
public class UserCenterController {

    private static final String UPLOAD_FOLDER="/static/img";

    //GetMapping 查找
    //PostMapping 添加
    //PutMapping  修改
    //DelateMapping 删除
    @Autowired
    UserService userService;
    @Autowired
    WalletOrderService walletOrderService;
    @Autowired
    CouponService couponService;
    @Autowired
    CouponController couponController;
    /*
    * 查找用户信息
    * @param token
    * @return 返回用户对象
    * */
/*    @ApiOperation(value = "查找用户信息",notes = "")
    @GetMapping("/userInfo/{token}")
    public Map findUserByToken(@PathVariable("token")String token){
        Map result =new HashMap();
        System.out.println(token);
        User user = userService.findUserByName(token);
        System.out.println(user);
        result.put("user",user);
        return  result;
    }*/

    /*
     * 查找用户信息
     * @param userId
     * @return 返回用户对象
     * */
    @ApiOperation(value = "查找用户信息",notes = "")
    @GetMapping("/userInfo/{userId}")
    public Map findUserById(@PathVariable("userId")Integer userId){
        Map result =new HashMap();
        System.out.println(userId);
        User user = userService.findUserByUserId(userId);
        System.out.println(user);
        result.put("user",user);
        return  result;
    }

    /*
     * 修改用户头像
     * @param token
     * @param multipartFile
     * @return 返回结果集合
     * */
/*    @ApiOperation(value = "修改用户头像",notes = "")
    @PutMapping("/userInfoHeadImg/{token}")
    public Map updateUserHeadImg(@PathVariable("token") String token, @RequestParam("multipartFile") MultipartFile multipartFile){
        Map result=new HashMap();
        String fileName=multipartFile.getOriginalFilename();
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String newFileName=dateFormat.format(new Date())+suffix;
        try {
            String classPath = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
            File file=new File(classPath+UPLOAD_FOLDER);
            System.out.println(file);
            if(!file.exists()){
                file.mkdirs();
            }
            multipartFile.transferTo(new File(file.getAbsoluteFile()+File.separator+newFileName));
            System.out.println(file.getAbsoluteFile()+File.separator+newFileName);
            User user = userService.findUserByName(token);
            user.setHeadimg(newFileName);
            userService.modifyUserInfo(user);
            result.put("code", 0);
            result.put("msg", "修改成功");
            result.put("img",newFileName);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg","图片上传失败");
        }
        return result;
    }*/

    /*
     * 修改用户头像
     * @param token
     * @param multipartFile
     * @return 返回结果集合
     * */
    @ApiOperation(value = "修改用户头像",notes = "")
    @PutMapping("/userInfoHeadImg/{userId}")
    public Map updateUserHeadImgByUserId(@PathVariable("userId") Integer userId, @RequestParam("multipartFile") MultipartFile multipartFile){
        Map result=new HashMap();
        String fileName=multipartFile.getOriginalFilename();
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String newFileName=dateFormat.format(new Date())+suffix;
        try {
            String classPath = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
            File file=new File(classPath+UPLOAD_FOLDER);
            System.out.println(file);
            if(!file.exists()){
                file.mkdirs();
            }
            multipartFile.transferTo(new File(file.getAbsoluteFile()+File.separator+newFileName));
            System.out.println(file.getAbsoluteFile()+File.separator+newFileName);
            User user = userService.findUserByUserId(userId);
            user.setHeadimg(newFileName);
            userService.modifyUserInfo(user);
            result.put("code", 0);
            result.put("msg", "修改成功");
            result.put("img",newFileName);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg","图片上传失败");
        }
        return result;
    }

    /*
    * 修改用户信息
    * @param userId
    * @return 返回结果集合
    * */
    @ApiOperation(value = "修改用户信息",notes = "")
    @PutMapping("/userInfoModify/{userId}")
    public Map updateUserInfo(@RequestBody User user,@PathVariable("userId")Integer userId){
        System.out.println(userId);
        System.out.println(user);
        Map result=new HashMap();
        try {
            int code = userService.modifyUserInfo(user);
            if(code==-1){
                result.put("code",-1);
                result.put("msg","用户名已存在");
            }if(code==-2){
                result.put("code",-2);
                result.put("msg","该号码已被绑定");
            }if(code==3){
                result.put("code",-3);
                result.put("msg","昵称已存在");
            } else{
                result.put("code", 0);
                result.put("msg", "修改成功");
            }
        }catch (UserException e){
            e.printStackTrace();
            result.put("code",500);
            result.put("msg",e.getMessage());
        }
        return result;
    }


    /*
    * 分页展示用户（后台管理）
    * @param pageSize
    * @param currentPage
    * @param userVo
    * @return 返回分页集合
    * */
    @ApiOperation(value = "展示用户分页列表",notes = "")
    @GetMapping("/userList")
    public PageInfo<User> pageUserList(@RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
                                     @RequestParam(value = "currentPage",defaultValue = "1",required = false)Integer currentPage,
                                     @RequestParam(value = "userVo",required = false)UserVo userVo){
        List<User> userList = userService.findUserByPage(pageSize, currentPage, userVo);
        PageInfo<User> pageInfo=new PageInfo<>(userList);
        return pageInfo;
    }


    /*
     * 根据钱包订单激活会员状态
     * @param userId
     * @return 返回结果集合
     * */
    @ApiOperation(value = "钱包订单时间激活会员状态",notes = "根据url中用户id查找订单中最近的会员充值订单，获取时间激活会员")
    @PutMapping("/vip/{userId}")
    public Map beVip(@PathVariable("userId")Integer userId){
        Map result=new HashMap();
        List<WalletOrder> walletVipOrder = walletOrderService.findVipOrderByUserId(userId);
        Date createtime = walletVipOrder.get(0).getWalletOrderCreatetime();
        Calendar vipTime=Calendar.getInstance();
        vipTime.setTime(createtime);
        //会员30天过期
        vipTime.add(Calendar.DATE,30);
        User user = userService.findUserByUserId(walletVipOrder.get(0).getUserId());
        //会员状态1是普通用户
        //修改会员状态为2
        user.setVipState(2);
        //设置会员有效时长
        user.setVipActivetime(vipTime.getTime());
        try {
            userService.updateByPrimaryKey(user);
            for(int i=0;i<4;i++) {
                couponController.addCoupon(userId);
            }
            result.put("code",0);
            result.put("msg","会员激活成功");
        } catch (UserCenterException e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg","会员激活失败");
        }
        return result;
    }

    /*
    * 判断用户会员状态是否过期
    * @param userId
    * @return 返回结果集合
    * */
    @ApiOperation(value = "查询用户会员时间是否到期",notes = "根据url中用户id查询会员有效期，失效修改状态")
    @PutMapping("/vipState/{userId}")
    public Map modifyVipState(@PathVariable("userId") Integer userId){
        User user = userService.selectByPrimaryKey(userId);
        Map result=new HashMap();
        //当前时间大于会员有效期，说明会员失效

            try {
                if(new Date().getTime()>user.getVipActivetime().getTime()) {
                    user.setVipState(1);
                    userService.updateByPrimaryKey(user);
                    result.put("code", 0);
                    result.put("msg", "更新会员状态成功");
                }else{
                    result.put("code", 1);
                    result.put("msg", "会员仍在有效期");
                }
            } catch (UserCenterException e) {
                result.put("code", 500);
                result.put("msg", "更新会员状态失败");
                e.printStackTrace();
            }
        return result;
    }
}
