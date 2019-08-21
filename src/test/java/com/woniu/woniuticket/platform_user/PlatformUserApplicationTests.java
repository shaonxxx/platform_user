package com.woniu.woniuticket.platform_user;

import com.woniu.woniuticket.platform_user.utils.UserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformUserApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(UserUtil.getRandomString(32));
    }

}
