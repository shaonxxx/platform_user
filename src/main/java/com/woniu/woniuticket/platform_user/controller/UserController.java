package com.woniu.woniuticket.platform_user.controller;

import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.UserService;
import com.woniu.woniuticket.platform_user.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 用户注册
     */
    @PostMapping("/user")
    public ModelAndView userRegisty(@Validated User user, BindingResult br){
        ModelAndView mv = new ModelAndView();
        System.out.println("**********进入注册方法**********");
        System.out.println("接收的参数："+user);
        //获取错误信息的总数
        int errorCount = br.getErrorCount();
        if(errorCount>0) {
            System.out.println("有错误消息");
            //获取那些错误消息字段
            FieldError nameError = br.getFieldError("userName");
            FieldError pwdError = br.getFieldError("password");
            FieldError emailError = br.getFieldError("email");
            FieldError mobileError = br.getFieldError("mobile");
            FieldError nicknameError = br.getFieldError("nickname");
            if (nameError != null) {
                //将验证的错误消息存进ModelAndView
                mv.addObject("nameError", nameError.getDefaultMessage());
            }
            if (pwdError != null) {
                mv.addObject("pwdError", pwdError.getDefaultMessage());
            }
            if (emailError != null) {
                mv.addObject("emailError", emailError.getDefaultMessage());
            }
            if (mobileError != null) {
                mv.addObject("mobileError", mobileError.getDefaultMessage());
            }
            if (nicknameError != null) {
                mv.addObject("nicknameError", nicknameError.getDefaultMessage());
            }
            mv.setViewName("/registry");
            return mv;
        }
        //对传入密码进行md5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        //设置用户32位邀请码
        user.setInviteCode(UserUtil.getRandomString(32));
        user.setRegistTime(new Date());
        user.setVipState((byte)0);
        user.setUserState((byte)0);
        System.out.println("\n最终插入数据："+user);
        int message = userService.insert(user);
        if (message==1){
            mv.addObject("message","注册成功,邮件已发送，请入邮箱激活。");
        }else {
            mv.addObject("message","注册失败，发生未知错误");
        }
        try {
            UserUtil.sendEmail(mailSender,user);
        } catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("/message");
            mv.addObject("message","注册失败，发生未知错误\n"+e);
            return mv;
        }
        System.out.println("**********退出注册方法**********");
        mv.setViewName("/message");
        return mv;
    }

    /**
     * 邮件激活
     * @return
     */
    @GetMapping("/user/email/{userId}")
    public ModelAndView emailActivate(@PathVariable Integer userId){
        System.out.println("**********进入邮件激活方法**********");
        ModelAndView mv = new ModelAndView();
        System.out.println(userId);
        User user = userService.selectByPrimaryKey(userId);
        System.out.println("根据id查找到的用户："+user);
        if(user.getUserState()==0) {
            int message = userService.updateStateByKey(userId);
            if (message == 1) {
                mv.addObject("message", "该账户激活成功！");
                mv.addObject("userLogin",user);
            } else {
                mv.addObject("message", "激活失败");
            }
        }else{
            mv.addObject("message", "该用户已经激活，请勿多次激活");
        }
        System.out.println("**********退出邮件激活方法**********");
        mv.setViewName("/message");
        return mv;
    }


    /**
     * 用户登录
     * @return
     */
    @GetMapping("/user")
    public void userLogin(User user){
        System.out.println("进入登录方法");
        System.out.println(user);
    }
}
