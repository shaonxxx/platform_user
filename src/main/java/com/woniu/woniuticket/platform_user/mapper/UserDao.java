package com.woniu.woniuticket.platform_user.mapper;

import com.woniu.woniuticket.platform_user.pojo.User;

public interface UserDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateStateByKey(Integer userId);
}