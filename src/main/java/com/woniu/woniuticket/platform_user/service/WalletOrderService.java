package com.woniu.woniuticket.platform_user.service;

import com.woniu.woniuticket.platform_user.pojo.Wallet;
import com.woniu.woniuticket.platform_user.pojo.WalletOrder;

import java.util.List;

public interface WalletOrderService {
    List<WalletOrder> findOrderByUserId(Integer userId);

    int updateWalletOrder(WalletOrder walletOrder);

    List<WalletOrder> findChargeOrderByTime(Integer userId);

    List<WalletOrder> findReduceOrderByTime(Integer userId);

    int createWalletOrder(WalletOrder walletOrder);

    WalletOrder findOrderByOrderId(Integer walletOrderId);

    List<WalletOrder> findVipOrderByUserId(Integer userId);
}
