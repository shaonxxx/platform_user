package com.woniu.woniuticket.platform_user.controller;

import com.alibaba.fastjson.JSON;
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
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
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
    public String userRegisty(@RequestBody @Validated User user, BindingResult br){ //@Validated
//        ModelAndView mv = new ModelAndView();
        Map<String,Object> verify = new HashMap();
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
                verify.put("nameError", nameError.getDefaultMessage());
            }
            if (pwdError != null) {
                verify.put("pwdError", pwdError.getDefaultMessage());
            }
            if (emailError != null) {
                verify.put("emailError", emailError.getDefaultMessage());
            }
            if (mobileError != null) {
                verify.put("mobileError", mobileError.getDefaultMessage());
            }
            if (nicknameError != null) {
                verify.put("nicknameError", nicknameError.getDefaultMessage());
            }

            return JSON.toJSONString(verify);
//            mv.setViewName(UserConstant.REGISTRY);
//            return mv;
        }
        if(user.getRegistCode()!=null && user.getRegistCode()!=""){
            System.out.println("有注册码");
            /*
                这里处理有注册码的情况，
                比如优惠券

                根据 ·注册码· 查找拥有该 ·邀请码· 用户，
                携带注册码用户 + 券
                携带邀请码用户 + 券

                1.根据注册码查拥有该邀请码用户（验证注册码正确性）
                2.根据查找到的用户id查钱包 + 券（修改券操作）
                3.注册用户 + 券（修改券操作）
                4.注册用户执行insert后才会有钱包，（注册完毕同时insert钱包）
                5.注册用户执行insert之后才能根据id查找钱包或者通过id修改钱包
                so，这段代码需要放在插入语句之后

             */
        }
        System.out.println();
        User findUserByName = userService.findUserByName(user.getUserName());
        User findUserByEmail = userService.findUserByEmail(user.getEmail());
        User findUserByMobile = userService.findUserByMobile(user.getMobile());
        //数据验证
        if(findUserByName!=null){
            if(user.getUserName().equals(findUserByName.getUserName())){
                verify.put("nameError", "用户名已被注册");
            }
            if(user.getEmail().equals(findUserByEmail.getEmail())){
                verify.put("emailError", "邮箱已被注册");
            }
            if(user.getMobile().equals(findUserByMobile.getMobile())){
                verify.put("mobileError", "该手机已被注册");
            }
//            mv.setViewName(UserConstant.REGISTRY);
            return JSON.toJSONString(verify);
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
            verify.put("message","注册成功,邮件已发送，请入邮箱激活。");
//            return null;
        }else {
            verify.put("message","注册失败，发生未知错误");
        }
        //邮件发送
        try {
            UserUtil.sendEmail(mailSender,user);
        } catch (Exception e) {
            e.printStackTrace();
//            mv.setViewName(UserConstant.MESSAGE);
            verify.put("message","注册失败，发生未知错误\n"+e);
            return JSON.toJSONString(verify);
        }
        System.out.println("**********退出注册方法**********");
        return JSON.toJSONString(verify);
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
    @PostMapping("/user/session")
    public String userLogin(String loginName,String password, Boolean remember,HttpServletRequest req){
        System.out.println("**********进入登录方法**********");
        System.out.println("登录使用账户："+loginName+",登录使用密码："+password+",是否选中30天套餐"+remember);
        Map<String,Object> verify = new HashMap();
        //账号密码格式验证
        if(loginName==null || loginName==""){
            verify.put("loginNameError","用户名不能为空");
        }
        if(password==null || password==""){
            verify.put("passwordError","密码不能为空");
        }
        if(verify.size()!=0){
            String json = JSON.toJSONString(verify);
            return json;
        }
        //用户名属性判断
        User findUser = userService.findUserByName(loginName);
        if (findUser==null){
            String emailE = UserConstant.EMAIL_GREP;
            if(loginName.matches(emailE)){
                findUser=userService.findUserByEmail(loginName);
            }else {
                findUser=userService.findUserByMobile(loginName);
            }
        }
        //账号密码数据匹配（返回键：message，值：错误信息）
        if(findUser==null){
            System.out.println("用户名不正确");
            verify.put("message","用户名或密码不正确");
        }else{
            if(!findUser.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
                System.out.println("密码不正确");
                verify.put("message","用户名或密码不正确");
            }
        }
        if(verify.size()!=0){
            String json = JSON.toJSONString(verify);
            return json;
        }
        HttpSession session = req.getSession();
        session.setAttribute(UserConstant.USER_LOGIN,findUser);
        if(remember) {
            session.setMaxInactiveInterval(43200);//设置单位为秒，设置为-1永不过期, 这里设置30天
        }
        System.out.println("**********登录完成**********");
        return null;
    }
}
