package com.woniu.woniuticket.platform_user.service.impl;

import com.woniu.woniuticket.platform_user.mapper.UserDao;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.UserService;
import com.woniu.woniuticket.platform_user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据手机号查找用户
     * @param mobile
     * @return
     */
    @Override
    public User findUserByMobile(String mobile) {
        return userDao.selectUserByMobile(mobile);
    }

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    @Override
    public User findUserByEmail(String email) {
        return userDao.selectUserByEmail(email);
    }

    /**
     * 根据条件动态查询
     * @param user
     * @return
     */
    @Override
    public User findUserByChoose(User user){
        return  userDao.selectUserByChoose(user);
    }

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

    /**
     * 根据主键修改用户激活状态
     * @param userId
     * @return
     */
    @Override
    public int updateStateByKey(Integer userId) {
        return userDao.updateStateByKey(userId);
    }

    /*
    * 根据用户id查找用户
    * @param userId
    * @return
    * */
    @Override
    public User findUserByUserId(Integer userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    /*
    * 修改用户信息
    * @param user
    * @return
    * */
    @Override
    public int modifyUserInfo(User user) {
        User dbuser = userDao.selectByPrimaryKey(user.getUserId());
        if(dbuser!=null){
            //数据库用户与当前用户是否为同一人
            if(!dbuser.getUserId().equals(user.getUserId())){
                if(dbuser.getUserName().equals(user.getUserName())) {
                    //用户名已存在
                    return -1;
                }if(dbuser.getMobile().equals(user.getMobile())){
                    //手机号已存在
                    return -2;
                }if(dbuser.getNickname().equals(user.getNickname())){
                    //昵称已存在
                    return -3;
                }
            }
        }
        return userDao.updateByPrimaryKeySelective(user);
    }


    /*
    * 用户分页，按条件查询user
    * @param pageSize
    * @param currentPage
    * @param userVo
    * @return
    * */
    @Override
    public List<User> findUserByPage(Integer pageSize, Integer currentPage, UserVo userVo) {
        return userDao.selectUserByPage(pageSize,currentPage,userVo);
    }
}
