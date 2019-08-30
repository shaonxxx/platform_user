package com.woniu.woniuticket.platform_user.service.impl;

import com.woniu.woniuticket.platform_user.mapper.WalletDao;
import com.woniu.woniuticket.platform_user.pojo.Wallet;
import com.woniu.woniuticket.platform_user.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletDao walletDao;

    @Override
    public Wallet findWalletByUserName(String userName) {
        return  walletDao.selectWalletByUserName(userName);
    }

    @Override
    public int modifyWallet(Wallet wallet) {
        return walletDao.updateByPrimaryKey(wallet);
    }

    @Override
    public Wallet findWalletByUserId(Integer userId) {
        return  walletDao.selectByPrimaryKey(userId);
    }

    @Override
    public int createWallet(Integer userId) {
        Wallet wallet = walletDao.selectWalletByUserId(userId);
        System.out.println(wallet);
        //数据库查找用户钱包 如果为空 创建一个钱包
        if (wallet != null) {
            //数据库存在钱包
            return 0;
        }else{
            Wallet newWallet = new Wallet();
            newWallet.setUserId(userId);
            newWallet.setResAmount((long) 0);
            return walletDao.insert(newWallet);
        }
    }
}
