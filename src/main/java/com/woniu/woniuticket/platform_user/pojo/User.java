package com.woniu.woniuticket.platform_user.pojo;

import java.util.Date;

public class User {
    private Integer userId;

    private String userName;

    private String password;

    private String email;

    private String mobile;

    private String nickname;

    private Date registTime;

    private Byte vipState;

    private Date vipActivetime;

    private String headimg;

    private String inviteCode;

    private String registCode;

    private Byte userState;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Byte getVipState() {
        return vipState;
    }

    public void setVipState(Byte vipState) {
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

    public Byte getUserState() {
        return userState;
    }

    public void setUserState(Byte userState) {
        this.userState = userState;
    }
}