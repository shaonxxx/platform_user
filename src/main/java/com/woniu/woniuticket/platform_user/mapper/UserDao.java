package com.woniu.woniuticket.platform_user.mapper;

import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserByUserName(String userName);

    List<User> selectUserByPage(@Param("pageSize") Integer pageSize, @Param("currentPage") Integer currentPage,
                                @Param("userVo") UserVo userVo);
}