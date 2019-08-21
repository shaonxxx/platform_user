package com.woniu.woniuticket.platform_user.controller;

import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/user")
    public void userRegisty(User user){
        System.out.println("进入注册方法");
    }

    /**
     * 用户登录
     * @return
     */
    @GetMapping("/user")
    public void userLogin(User user){
        System.out.println("进入登录方法");
    }
}
