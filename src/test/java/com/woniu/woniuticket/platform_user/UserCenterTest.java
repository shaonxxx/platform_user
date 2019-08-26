package com.woniu.woniuticket.platform_user;

import com.woniu.woniuticket.platform_user.controller.UserCenterController;
import com.woniu.woniuticket.platform_user.controller.UserController;
import com.woniu.woniuticket.platform_user.mapper.UserDao;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.UserService;
import com.woniu.woniuticket.platform_user.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCenterTest {
    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;
    @Autowired
    UserCenterController userCenterController;

    @Test
    public void testListUser(){
        UserVo userVo=new UserVo();
        userVo.setUserName("aaa");
        List<User> list = userDao.selectUserByPage(5, 1,userVo );
        for (User lists : list) {
            System.out.println(lists.getUserName());
        }
    }

    @Test
    public void vipOut() {
        User user = userDao.selectByPrimaryKey(1);
        if (new Date().getTime() > user.getVipActivetime().getTime()) {
            user.setVipState(1);
            userDao.updateByPrimaryKey(user);
        }
        System.out.println(user.getVipState());
    }

    @Test
    public void beVip(){
        userCenterController.beVip(1);
    }

    @Test
    public void testFindUser() {
        User user = userDao.selectUserByUserName("aaa");
        System.out.println(user.getUserName());
    }

}
