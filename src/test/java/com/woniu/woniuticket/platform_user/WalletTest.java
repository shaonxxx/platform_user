package com.woniu.woniuticket.platform_user;

import com.woniu.woniuticket.platform_user.controller.WalletController;
import com.woniu.woniuticket.platform_user.service.WalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletTest {
    @Autowired
    WalletService walletService;
    @Autowired
    WalletController walletController;


    @Test
    public void testCreateWallet(){
        walletService.createWallet(1);
    }

    @Test
    public void findWalletByName(){
        walletService.findWalletByUserName("aaa");
    }

    @Test
    public void testAddMoney(){
        walletController.AddMoney(1);
    }

    @Test
    public void testReduceMoney(){
        walletController.ReduceMoney(1);
    }

}
