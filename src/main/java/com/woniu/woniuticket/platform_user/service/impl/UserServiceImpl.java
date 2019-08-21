package com.woniu.woniuticket.platform_user.service.impl;

import com.woniu.woniuticket.platform_user.mapper.UserDao;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户姓名查找用户
     * @param userName
     * @return
     */
    @Override
    public User findUserByName(String userName) {
        return null;
    }

    /**
     * 根据用户id删除用户
     * @param userId
     * @return
     */
    @Override
    public int removeByPrimaryKey(Integer userId) {
        return userDao.deleteByPrimaryKey(userId);
    }

    /**
     * 添加用户
     * @param record
     * @return
     */
    @Override
    public int insert(User record) {
        return userDao.insert(record);
    }

    /**
     * 有条件的添加用户
     * @param record
     * @return
     */
    @Override
    public int insertSelective(User record) {
        return userDao.insertSelective(record);
    }

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    @Override
    public User selectByPrimaryKey(Integer userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    /**
     * 根据用户id选择性修改
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据用户id修改用户
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(User record) {
        return userDao.updateByPrimaryKey(record);
    }
}