package com.woniu.woniuticket.platform_user.controller;


import com.github.pagehelper.PageInfo;
import com.woniu.woniuticket.platform_user.exception.UserException;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.UserService;

import com.woniu.woniuticket.platform_user.vo.UserVo;
//import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserCenterController {

    //GetMapping 查找
    //PostMapping 添加
    //PutMapping  修改
    //DelateMapping 删除
    @Autowired
    UserService userService;
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
    @PutMapping("/userInfo")
    public Map updateUserInfo( User user){
        Map result=new HashMap();
        try {
            int code = userService.updateByPrimaryKey(user);
            if (code == 0) {
                result.put("code", 0);
                result.put("msg", "修改成功");
            } else if(code==500){
                result.put("code", 500);
                result.put("msg", "修改失败");
            } if(code==-1){
                result.put("code",-1);
                result.put("msg","用户名已存在");
            }if(code==-2){
                result.put("code",-2);
                result.put("msg","该号码已被绑定");
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
}
