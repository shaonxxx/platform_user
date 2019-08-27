package com.woniu.woniuticket.platform_user.controller;


import com.github.pagehelper.PageInfo;
import com.woniu.woniuticket.platform_user.exception.UserException;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.pojo.WalletOrder;
import com.woniu.woniuticket.platform_user.service.UserService;

import com.woniu.woniuticket.platform_user.service.WalletOrderService;
import com.woniu.woniuticket.platform_user.vo.UserVo;
//import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class UserCenterController {

    private static  final String UPLOAD_FOLDER="/static/img";

    //GetMapping 查找
    //PostMapping 添加
    //PutMapping  修改
    //DelateMapping 删除
    @Autowired
    UserService userService;
    @Autowired
    WalletOrderService walletOrderService;
    /*
    * 查找用户信息
    *
    * */
/*    @GetMapping("/userInfo")
    @ResponseBody
    public User findUserByToken(HttpServletRequest request, HttpServletResponse response, UsernamePasswordToken token){
        String userName = (String) token.getPrincipal();
        User user = userService.findUserByName(userName);
        return  user;
    }*/



    /*
    * 修改用户信息
    *
    * */

    @RequestMapping("/userInfo")
    public Map updateUserInfo(Integer userId,@RequestParam("multipartFile") MultipartFile multipartFile,String path){
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
        } catch (java.io.IOException e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg","图片上传失败");
        }
        try {
            User user = userService.findUserByUserId(userId);
            user.setHeadimg(newFileName);
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
    *
    * */

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
     *
     * */

    @PutMapping("/vip/{userId}")
    public Map beVip(@PathVariable("userId")Integer userId){
        Map result=new HashMap();
        List<WalletOrder> walletVipOrder = walletOrderService.findVipOrderByUserId(userId);
        Date createtime = walletVipOrder.get(0).getWalletOrderCreatetime();
        Calendar vipTime=Calendar.getInstance();
        vipTime.setTime(createtime);
        vipTime.add(Calendar.DATE,30);
        User user = userService.findUserByUserId(walletVipOrder.get(0).getUserId());
        user.setVipState(2);
        user.setVipActivetime(vipTime.getTime());
        try {
            userService.updateByPrimaryKey(user);
            result.put("code",0);
            result.put("msg","会员激活成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg","会员激活失败");
        }
        return result;
    }

    /*
    * 判断用户会员状态是否过期
    *
    * */
    @PutMapping("/vipState/{userId}")
    public Map modifyVipState(@PathVariable("userId") Integer userId){
        User user = userService.selectByPrimaryKey(userId);
        Map result=new HashMap();
        if(new Date().getTime()>user.getVipActivetime().getTime()){
            user.setVipState(1);
            try {
                userService.updateByPrimaryKey(user);
                result.put("code",0);
                result.put("msg","更新会员状态成功");
            } catch (Exception e) {
                e.printStackTrace();
                result.put("code", 500);
                result.put("msg", "更新会员状态失败");
            }
        }
        return result;
    }
}
