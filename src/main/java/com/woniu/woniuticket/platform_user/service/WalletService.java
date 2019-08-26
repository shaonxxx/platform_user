package com.woniu.woniuticket.platform_user.service;

import com.woniu.woniuticket.platform_user.pojo.Wallet;

public interface WalletService {
    Wallet findWalletByUserName(String userName);

    int modifyWallet(Wallet wallet);

    Wallet findWalletByUserId(Integer userId);

    int createWallet(Integer userId);
}
