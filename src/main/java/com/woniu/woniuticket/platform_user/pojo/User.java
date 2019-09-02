package com.woniu.woniuticket.platform_user.pojo;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer userId;

    @NotNull(message = "姓名不能为空")
//    @NotEmpty(message ="用户名不能为空")
    @Size(min = 6,max = 20,message = "用户名长度为{min}-{max}")
    private String userName;

    @NotEmpty(message ="密码不能为空")
//    @NotNull(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;

    @NotEmpty(message ="手机号不能为空")
    @Pattern(regexp = "^1[38]\\d{9}$",message = "手机号码格式不正确")
    private String mobile;

    @NotNull(message ="昵称不能为空")
    private String nickname;

    private Date registTime;

    private Integer vipState;

    private Date vipActivetime;

    private String headimg;

    private String inviteCode;

    private String registCode;  //注册码

    private Integer userState;

    private String gender;

    private Date birth;

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nickname='" + nickname + '\'' +
                ", registTime=" + registTime +
                ", vipState=" + vipState +
                ", vipActivetime=" + vipActivetime +
                ", headimg='" + headimg + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", registCode='" + registCode + '\'' +
                ", userState=" + userState +
                ", gender='" + gender + '\'' +
                ", birth=" + birth +
                '}';

    }
}