package com.woniu.woniuticket.platform_user.mapper;

import com.woniu.woniuticket.platform_user.pojo.WalletOrder;

import java.util.List;

public interface WalletOrderDao {
    int deleteByPrimaryKey(Integer walletOrderId);

    int insert(WalletOrder record);

    int insertSelective(WalletOrder record);

    WalletOrder selectByPrimaryKey(Integer walletOrderId);

    int updateByPrimaryKeySelective(WalletOrder record);

    int updateByPrimaryKey(WalletOrder record);

    WalletOrder selectOrderByUserId(Integer userId);

    List<WalletOrder> selectChargeOrderByTime(Integer userId);

    List<WalletOrder> selectReduceOrderByTime(Integer userId);

    List<WalletOrder> selectOrderList(Integer userId);

    List<WalletOrder> selectVipOrderByTime(Integer userId);
}