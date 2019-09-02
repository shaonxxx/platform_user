package com.woniu.woniuticket.platform_user.service.impl;

import com.woniu.woniuticket.platform_user.mapper.WalletDao;
import com.woniu.woniuticket.platform_user.mapper.WalletOrderDao;
import com.woniu.woniuticket.platform_user.pojo.WalletOrder;
import com.woniu.woniuticket.platform_user.service.WalletOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletOrderServiceImpl implements WalletOrderService {
    @Autowired
    WalletOrderDao walletOrderDao;

    /*
    * 根据用户id分页查询钱包订单
    * @param userId
    * @return
    * */
    @Override
    public List<WalletOrder> findOrderByUserId(Integer pageSize,Integer currentPage,Integer userId) {
        return walletOrderDao.selectOrderList(pageSize,currentPage,userId);
    }

    @Override
    public int updateWalletOrder(WalletOrder walletOrder) {
        return walletOrderDao.updateByPrimaryKey(walletOrder);
    }

    @Override
    public List<WalletOrder> findChargeOrderByTime(Integer userId) {
        return walletOrderDao.selectChargeOrderByTime(userId);
    }

    @Override
    public List<WalletOrder> findReduceOrderByTime(Integer userId) {
        return walletOrderDao.selectReduceOrderByTime(userId);
    }

    @Override
    public int createWalletOrder(WalletOrder walletOrder) {
        return walletOrderDao.insert(walletOrder);
    }

    @Override
    public WalletOrder findOrderByOrderId(Integer walletOrderId) {
        return walletOrderDao.selectByPrimaryKey(walletOrderId);
    }

    @Override
    public List<WalletOrder> findVipOrderByUserId(Integer userId) {
        return walletOrderDao.selectVipOrderByTime(userId);
    }
}
