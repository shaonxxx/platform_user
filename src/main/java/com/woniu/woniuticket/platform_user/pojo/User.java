package com.woniu.woniuticket.platform_user.pojo;

import java.util.Date;

public class User {
    private Integer userId;

    private String userName;

    private String password;

    private String mobile;

    private String nickname;

    private Date registTime;

    private Integer vipState;

    private Date vipActivetime;

    private String heading;

    private String inviteCode;

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

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nickname='" + nickname + '\'' +
                ", registTime=" + registTime +
                ", vipState=" + vipState +
                ", vipActivetime=" + vipActivetime +
                ", heading='" + heading + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", registCode='" + registCode + '\'' +
                ", userState=" + userState +
                ", email='" + email + '\'' +
                '}';
    }
}