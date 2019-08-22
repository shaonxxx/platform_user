package com.woniu.woniuticket.platform_user.constant;

/**
 * 该类为用户系统常量池
 * 定义常量请在后面写上注释
 */
public class UserConstant {


    public final static String REGISTRY = "/registry";  //注册页面

    public final static String LOGIN = "/login";        //登录页面

    public final static String INDEX = "/index";        //主页

    public final static String MESSAGE = "/message";    //登录注册操作后跳转页面，（暂定，测试用）

    public final static String USER_LOGIN = "userLogin";//登录成功存在session中的user对象名

    //email的正则表达式
    public final static String EMAIL_GREP = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
}
