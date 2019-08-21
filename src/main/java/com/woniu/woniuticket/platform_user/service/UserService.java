package com.woniu.woniuticket.platform_user.service;

import com.woniu.woniuticket.platform_user.pojo.User;

public interface UserService {


    public User findUserByName(String userName);

    int removeByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);





}