package com.woniu.woniuticket.platform_user.pojo;

import java.util.Date;

public class WalletOrder {
    private Integer walletOrderId;

    private Date walletOrderCreatetime;

    private Integer walletOrderMoney;
    //钱包订单状态 walletOrderState
    //1已完成
    //2未完成
    //3已删除
    private Integer walletOrderState;

    private Integer userId;
    //订单支付类型 walletOrderType
    //1购票观影支付 余额-
    //2会员充值支付 余额-
    //3余额充入 余额+
    private Integer walletOrderType;

    public Integer getWalletOrderType() {
        return walletOrderType;
    }

    public void setWalletOrderType(Integer walletOrderType) {
        this.walletOrderType = walletOrderType;
    }

    public Integer getWalletOrderId() {
        return walletOrderId;
    }

    public void setWalletOrderId(Integer walletOrderId) {
        this.walletOrderId = walletOrderId;
    }

    public Date getWalletOrderCreatetime() {
        return walletOrderCreatetime;
    }

    public void setWalletOrderCreatetime(Date walletOrderCreatetime) {
        this.walletOrderCreatetime = walletOrderCreatetime;
    }

    public Integer getWalletOrderMoney() {
        return walletOrderMoney;
    }

    public void setWalletOrderMoney(Integer walletOrderMoney) {
        this.walletOrderMoney = walletOrderMoney;
    }

    public Integer getWalletOrderState() {
        return walletOrderState;
    }

    public void setWalletOrderState(Integer walletOrderState) {
        this.walletOrderState = walletOrderState;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}