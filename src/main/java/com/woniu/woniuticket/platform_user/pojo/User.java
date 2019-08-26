package com.woniu.woniuticket.platform_user.pojo;

import java.util.Date;

public class User {
    private Integer userId;

    private String userName;

    private String password;

    private String mobile;
    //昵称
    private String nickname;
    //注册时间
    private Date registTime;
    //VIP状态
    private Integer vipState;
    //VIP到期时间
    private Date vipActivetime;
    //头像
    private String headimg;
    //邀请码
    private String inviteCode;
    //注册码
    private String registCode;

    private Integer userState;

    private String email;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Integer getVipState() {
        return vipState;
    }

    public void setVipState(Integer vipState) {
        this.vipState = vipState;
    }

    public Date getVipActivetime() {
        return vipActivetime;
    }

    public void setVipActivetime(Date vipActivetime) {
        this.vipActivetime = vipActivetime;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getRegistCode() {
        return registCode;
    }

    public void setRegistCode(String registCode) {
        this.registCode = registCode;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}