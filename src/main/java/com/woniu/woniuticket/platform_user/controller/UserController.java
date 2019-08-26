package com.woniu.woniuticket.platform_user.controller;

import com.woniu.woniuticket.platform_user.constant.UserConstant;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.UserService;
import com.woniu.woniuticket.platform_user.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 用户注册
     * @param user
     * @param br
     * @return
     */
    @PostMapping("/user")
    public ModelAndView userRegisty(@Validated User user, BindingResult br){
        ModelAndView mv = new ModelAndView();
        System.out.println("**********进入注册方法**********");
        System.out.println("接收的参数："+user);
        //获取错误信息的总数
        int errorCount = br.getErrorCount();
        //格式验证
        if(errorCount>0) {
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
            mv.setViewName(UserConstant.REGISTRY);
            return mv;
        }
        System.out.println();
        User findUserByName = userService.findUserByName(user.getUserName());
        User findUserByEmail = userService.findUserByEmail(user.getEmail());
        User findUserByMobile = userService.findUserByMobile(user.getMobile());
        //数据验证
        if(findUserByName!=null){
            if(user.getUserName().equals(findUserByName.getUserName())){
                mv.addObject("nameError", "用户名已被注册");
            }
            if(user.getEmail().equals(findUserByEmail.getEmail())){
                mv.addObject("emailError", "邮箱已被注册");
            }
            if(user.getMobile().equals(findUserByMobile.getMobile())){
                mv.addObject("mobileError", "该手机已被注册");
            }
            mv.setViewName(UserConstant.REGISTRY);
            return mv;
        }
        //对传入密码进行md5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        //设置用户32位邀请码
        user.setInviteCode(UserUtil.getRandomString(32));
        user.setRegistTime(new Date());
        user.setVipState(0);
        user.setUserState(0);
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
            mv.setViewName(UserConstant.MESSAGE);
            mv.addObject("message","注册失败，发生未知错误\n"+e);
            return mv;
        }
        System.out.println("**********退出注册方法**********");
        mv.setViewName(UserConstant.MESSAGE);
        return mv;
    }

    /**
     * 邮件激活
     * @param userId
     * @return
     */
    @GetMapping("/user/email/{userId}")
    public ModelAndView emailActivate(@PathVariable Integer userId,HttpServletRequest req){
        System.out.println("**********进入邮件激活方法**********");
        ModelAndView mv = new ModelAndView();
        System.out.println(userId);
        User user = userService.selectByPrimaryKey(userId);
        System.out.println("根据id查找到的用户："+user);
        if(user.getUserState()==0) {
            int message = userService.updateStateByKey(userId);
            if (message == 1) {
                mv.addObject("message", "该账户激活成功！");
                req.getSession().setAttribute(UserConstant.USER_LOGIN,user);
            } else {
                mv.addObject("message", "激活失败");
            }
        }else{
            mv.addObject("message", "该用户已经激活，请勿多次激活");
        }
        System.out.println("**********退出邮件激活方法**********");
        mv.setViewName(UserConstant.MESSAGE);
        return mv;
    }


    /**
     * 用户登录(普通登录)
     * @return
     */
    @GetMapping("/user")
    public ModelAndView userLogin(String loginName, String password, HttpServletRequest req){
        System.out.println("**********进入登录方法**********");
        ModelAndView mv = new ModelAndView();
        System.out.println("登录使用账户："+loginName+",登录使用密码："+password);
        //账号密码格式验证
        if(loginName==null || loginName==""){
            mv.addObject("message","用户名不能为空");
        }
        if(password==null || password==""){
            mv.addObject("message","密码不能为空");
        }
        //用户名属性判断
        User findUser = userService.findUserByName(loginName);
        if (findUser==null){
            System.out.println("不是用户名");
            String emailE = UserConstant.EMAIL_GREP;
            if(loginName.matches(emailE)){
                System.out.println("是邮箱");
                findUser=userService.findUserByEmail(loginName);
            }else {
                System.out.println("是手机");
                findUser=userService.findUserByMobile(loginName);
            }
        }
        //账号密码数据匹配（返回键：message，值：错误信息）
        if(findUser==null){
            System.out.println("用户名不正确");
            mv.addObject("message","用户名或密码不正确");
            mv.setViewName(UserConstant.LOGIN);
            return mv;
        }else{
            if(!findUser.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
                System.out.println("密码不正确");
                mv.addObject("message","用户名或密码不正确");
                mv.setViewName(UserConstant.LOGIN);
                return mv;
            }
        }
        req.getSession().setAttribute(UserConstant.USER_LOGIN,findUser);
        mv.setViewName(UserConstant.INDEX);
        System.out.println("**********登录完成**********");
        return mv;
    }
}
