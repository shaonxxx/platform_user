package com.woniu.woniuticket.platform_user;

import com.woniu.woniuticket.platform_user.controller.CouponController;
import com.woniu.woniuticket.platform_user.controller.UserCenterController;
import com.woniu.woniuticket.platform_user.controller.WalletController;
import com.woniu.woniuticket.platform_user.mapper.CouponDao;
import com.woniu.woniuticket.platform_user.mapper.UserDao;
import com.woniu.woniuticket.platform_user.pojo.Coupon;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.CouponService;
import com.woniu.woniuticket.platform_user.service.WalletService;
import com.woniu.woniuticket.platform_user.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformUserApplicationTests {

    @Test
    public void contextLoads() {
    }



}
