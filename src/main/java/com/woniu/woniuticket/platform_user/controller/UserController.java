package com.woniu.woniuticket.platform_user.controller;

import com.alibaba.fastjson.JSON;
import com.woniu.woniuticket.platform_user.constant.UserConstant;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param user
     * @param br
     * @return
     */
    @Transactional
    @PostMapping("/user")
    public String userRegisty(@RequestBody @Validated User user, BindingResult br){ //@Validated
        Map<String,Object> verify = new HashMap();      //存放返回消息
        System.out.println("**********进入注册方法**********");
        System.out.println("接收的参数："+user);
        //格式验证（返回json_Map数据）
        String result = userService.fomatVerify(verify,br);
        if(result!=null){
            return result;
        }
        //数据验证（返回json_Map数据）
        result = userService.dataVerify(user,verify);
        if(result!=null){
            return result;
        }

        //验证完毕进行初始化
        user = userService.userInit(user);

        if(user.getRegistCode()!=null && user.getRegistCode()!="" ){
            /*有注册码的处理*/
            if(user.getRegistCode().length()==32){
                System.out.println("有注册码");
                //根据注册码查找父用户
                User findUserFather = userService.findUserByRegistCode(user.getRegistCode());
                if(findUserFather==null){
                    verify.put("registryCodeError","无效的注册码");
                    return JSON.toJSONString(verify);
                }
                userService.registryCode(user,findUserFather.getUserId());
                userService.sendEmail(user,verify);
                verify.put("message","注册并发送激活邮件成功");
            }else{
                verify.put("registryCodeError","无效的注册码");
                return JSON.toJSONString(verify);
            }
        }else{
            /*无注册码的处理*/
            System.out.println("无注册码");
            userService.registryNoCode(user);
            String email = userService.sendEmail(user,verify);
            if(email!=null){
                return email;
            }
            verify.put("message","注册并发送激活邮件成功");
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
    public String emailActivate(@PathVariable Integer userId,HttpServletRequest req){
        System.out.println("**********进入邮件激活方法**********");
        System.out.println(userId);
        User user = userService.selectByPrimaryKey(userId);
        System.out.println("根据id查找到的用户："+user);
        if(user.getUserState()==0) {
            int message = userService.updateStateByKey(userId);
            if (message == 1) {
                req.getSession().setAttribute(UserConstant.USER_LOGIN,user);
            } else {
                return "激活失败";
            }
        }else{
            return "该用户已经激活，请勿多次激活";
        }
        System.out.println("**********退出邮件激活方法**********");
        return "邮件激活成功...";
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
        //用户名属性判断(判断登录用户输入的邮箱还是手机还是用户名)
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
