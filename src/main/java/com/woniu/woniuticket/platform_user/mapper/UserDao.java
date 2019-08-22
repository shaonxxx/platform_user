package com.woniu.woniuticket.platform_user.mapper;

import com.woniu.woniuticket.platform_user.pojo.User;

public interface UserDao {
    User selectUserByName(String userName);

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateStateByKey(Integer userId);

    User selectUserByChoose(User user);

    User selectUserByEmail(String email);

    User selectUserByMobile(String mobile);
}