package com.woniu.woniuticket.platform_user.service;

import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.vo.UserVo;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    String dataVerify(User user, Map verify);

    String fomatVerify(Map verify, BindingResult br);

    void registryCode(User user,Integer fatherId);

    void registryNoCode(User user);

    User userInit(User user);

    String sendEmail(User user,Map verify);

    String mobileFormatVerify(String mobile,Map verify);

    String smsCodeVerify(HttpServletRequest req,Map verify,String mobile,String code);


}
