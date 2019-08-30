package com.woniu.woniuticket.platform_user.service.impl;

import com.alibaba.fastjson.JSON;
import com.woniu.woniuticket.platform_user.mapper.UserDao;
import com.woniu.woniuticket.platform_user.pojo.User;
import com.woniu.woniuticket.platform_user.service.CouponService;
import com.woniu.woniuticket.platform_user.service.UserService;
import com.woniu.woniuticket.platform_user.service.WalletService;
import com.woniu.woniuticket.platform_user.utils.UserUtil;
import com.woniu.woniuticket.platform_user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    CouponService couponService;

    @Autowired
    WalletService walletService;

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
        return userDao.selectUserByName(userName);
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

    /**
     * 根据注册码查找用户
     * @param registCode
     * @return
     */
    @Override
    public User findUserByRegistCode(String registCode) {
        return userDao.selectByRegistCode(registCode);
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


    /**
     * 用户注册数据验证（普通注册）
     * @param user      注册信息
     * @param verify    验证结果容器
     * @return
     */
    public String dataVerify(User user, Map verify){
        User findUserByName = findUserByName(user.getUserName());
        User findUserByEmail = findUserByEmail(user.getEmail());
        User findUserByMobile = findUserByMobile(user.getMobile());
        //数据验证
        if(findUserByName!=null){
            if(user.getUserName().equals(findUserByName.getUserName())){
                verify.put("nameError", "用户名已被注册");
            }
            if(user.getEmail().equals(findUserByEmail.getEmail())){
                verify.put("emailError", "邮箱已被注册");
            }
            if(user.getMobile().equals(findUserByMobile.getMobile())){
                verify.put("mobileError", "该手机已被注册");
            }
//            mv.setViewName(UserConstant.REGISTRY);
            return JSON.toJSONString(verify);
        }
        return null;
    }

    /**
     * 用户注册格式验证（普通注册）
     * @param verify    验证结果容器
     * @param br        格式验证
     * @return
     */
    public String fomatVerify(Map verify, BindingResult br){
        //获取错误信息的总数
        int errorCount = br.getErrorCount();
        //格式验证
        if(errorCount>0) {
            //获取错误消息字段
            FieldError nameError = br.getFieldError("userName");
            FieldError pwdError = br.getFieldError("password");
            FieldError emailError = br.getFieldError("email");
            FieldError mobileError = br.getFieldError("mobile");
            FieldError nicknameError = br.getFieldError("nickname");
            if (nameError != null) {
                //将验证的错误消息存进ModelAndView
                verify.put("nameError", nameError.getDefaultMessage());
            }
            if (pwdError != null) {
                verify.put("pwdError", pwdError.getDefaultMessage());
            }
            if (emailError != null) {
                verify.put("emailError", emailError.getDefaultMessage());
            }
            if (mobileError != null) {
                verify.put("mobileError", mobileError.getDefaultMessage());
            }
            if (nicknameError != null) {
                verify.put("nicknameError", nicknameError.getDefaultMessage());
            }
            return JSON.toJSONString(verify);
        }
        return null;
    }

    /**
     * 控制事务进行处理用户注册（有注册码）
     *   1.插入用户（获取主键）
     *   2.插入钱包
     *   3.插入优惠券
     *   4.父用户修改优惠券(根据父用户id)
     * @param user
     * @param fatherId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void registryCode(User user,Integer fatherId)  {
        try {
            //用户插入
            int code1 = insert(user);
            System.out.println("插入后获取主键了没："+user+"\n插入用户的状态："+code1);
            //插入钱包
            int code2 = walletService.createWallet(user.getUserId());
            System.out.println("插入钱包的状态2："+code2);
            //注册用户插入优惠券
            int code3 = couponService.createCoupon(user.getUserId());
            System.out.println("插入优惠券的状态3："+code3);
            //父用户插入优惠券
            int code4 = couponService.createCoupon(fatherId);
            System.out.println("插入优惠券的状态4："+code4);
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 邮件发送
     */
    public String sendEmail(User user,Map verify){
        //激活邮件发送
        try {
            UserUtil.sendEmail(mailSender,user);
        } catch (Exception e) {
            e.printStackTrace();
            verify.put("message","注册失败，发生未知错误\n"+e);
            return JSON.toJSONString(verify);
        }
        return null;
    }


    /**
     * 控制事务进行处理用户注册（无注册码）
     * @param user
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void registryNoCode(User user)  {
        try {
            //用户插入
            int code1 = insert(user);
            System.out.println("插入后获取主键了？："+user+"\n插入用户的状态："+code1);
            //插入钱包
            int code2 = walletService.createWallet(user.getUserId());
            System.out.println("插入钱包的状态2："+code2);
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 对注册用户进行初始化赋值
     * @param user
     * @return
     */
    @Override
    public User userInit(User user){
        //对传入密码进行md5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        //设置用户32位邀请码
        user.setInviteCode(UserUtil.getRandomString(32));
        user.setRegistTime(new Date());
        user.setVipState(0);
        user.setUserState(0);
        System.out.println("\n最终插入数据："+user);
        return user;
    }


    /**
     * 手机号格式验证
     * @param mobile
     * @return
     */
    @Override
    public String mobileFormatVerify(String mobile,Map verify) {
        //手机号判空
        if(mobile.trim().equals("") || mobile == null) {
            verify.put("mobileError","手机号码为空！");
            return JSON.toJSONString(verify);
        }
        //手机号码格式判断
        if(!Pattern.matches("^1[3|4|5|7|8]\\d{9}$", mobile)) {
            verify.put("mobileError","手机格式错误！");
            return JSON.toJSONString(verify);
        }
        return null;
    }

    /**
     * 短信验证码的验证
     * @param req
     * @param verify
     * @param mobile
     * @param code
     * @return
     */
    @Override
    public String smsCodeVerify(HttpServletRequest req,Map verify,String mobile,String code) {
        //从session中拿出数据
        HttpSession session = req.getSession();
        String code_session = (String)session.getAttribute("code");
        String number = (String)session.getAttribute("number");
        Long time = (Long)session.getAttribute("time");
        //清除session中的数据
        session.removeAttribute("code");
        session.removeAttribute("number");
        session.removeAttribute("time");
        //验证码判空
        if(code_session == null || code_session.trim().equals("")) {
            verify.put("codeError","验证码失效！");
            return JSON.toJSONString(verify);
        }
        //验证码登录时效1分钟
        long ll = System.currentTimeMillis() - time;
        long cha = ll/1000;
        System.out.println(ll+"差："+cha+"秒");
        //验证码时效验证
        if((System.currentTimeMillis() - time) / 1000 > 60) {
            verify.put("message","验证码已过期！");
            return JSON.toJSONString(verify);
        }
        //发送验证码的手机号码和登录时得到手机号码必须一致
        if(!number.trim().equalsIgnoreCase(mobile)) {
            verify.put("message","手机号码不一致！");
            return JSON.toJSONString(verify);
        }

        if(!code_session.trim().equalsIgnoreCase(code)) {
            verify.put("message","验证码错误！");
            return JSON.toJSONString(verify);
        }
        return null;
    }





}
