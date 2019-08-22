package com.woniu.woniuticket.platform_user.utils;

import com.woniu.woniuticket.platform_user.pojo.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

public class UserUtil {

    /**
     * shiro中散列算法（弃用，表中没设计盐值）
     * @param password  原生密码
     * @param salt      盐值
     * @return
     */
    public static Md5Hash getMd5(String password,String salt){
        //迭代次数固定为10
        int hashIterations=10;
        return new Md5Hash(password,salt,hashIterations);
    }

    /**
     * 生成32位随机邀请码
     * @param length  生成随机码的长度
     * @return
     */
    public static String getRandomString(int length){
        //1.  定义一个字符串（A-Z，a-z，0-9）即62个数字字母；
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //2.  由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //3.  长度为几就循环几次
        for(int i=0; i<length; ++i){
            //从62个的数字或字母中选择
            int number=random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    /**
     * 发送邮件
     */
    public static void sendEmail(JavaMailSender mailSender,User user) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setSubject("邮件激活");
        helper.setText("<a href=\"localhost:8080/user/email/"+user.getUserId()+"\">邮箱激活</a>",true);
        helper.setFrom("snail_gclq@163.com");
        helper.setTo(user.getEmail());
        mailSender.send(mimeMessage);
    }


}
