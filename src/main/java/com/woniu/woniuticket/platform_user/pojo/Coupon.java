package com.woniu.woniuticket.platform_user.pojo;

import java.util.Date;

public class Coupon {
    private Integer couponId;

    private Integer userId;
    //抵扣金额
    private Float amount;
    //有效时间
    private Date activeTime;
    //状态
    private Integer state;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "couponId=" + couponId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", activeTime=" + activeTime +
                ", state=" + state +
                '}';
    }
}