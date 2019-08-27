package com.woniu.woniuticket.platform_user.service;

import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.vo.UserVo;

import java.util.List;

public interface UserService {
    User findUserByEmail(String userName);

    User findUserByChoose(User user);

    User findUserByName(String userName);

    int removeByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> findUserByPage(Integer pageSize, Integer currentPage, UserVo userVo);

    int updateStateByKey(Integer userId);

    User findUserByMobile(String userName);

    User findUserByUserId(Integer userId);

    int modifyUserInfo(User user);

    User findUserByRegistCode(String registCode);
}
