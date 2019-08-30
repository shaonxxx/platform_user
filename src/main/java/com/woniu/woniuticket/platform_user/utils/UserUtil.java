package com.woniu.woniuticket.platform_user.utils;

import com.woniu.woniuticket.platform_user.pojo.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

public class UserUtil {
    /**
     * 短信发送
     * @param phoneNumber   手机号码
     * @param code          随机验证码
     * @return
     * @throws Exception
     */
    public static boolean sendSmsCode(String phoneNumber, String code) throws Exception {
        System.out.println("发送短信接收的参数："+phoneNumber+":"+code);
        String str_code = URLEncoder.encode("#code#=" + code, "UTF-8");
        //包装好URL对象，将接口地址包装在此对象中
        URL url = new URL("http://v.juhe.cn/sms/send?mobile=" + phoneNumber +
                "&tpl_id=181757&tpl_value=" + str_code + "&key=78652933b3e4e998def477255e7b28ca");
        /* 短信模板id */                            /* 短信应用接口的key */
        System.out.println("封装的接口地址："+url);
        //打开连接，得到连接对象
        URLConnection connection = url.openConnection();
        //向服务器发送连接请求
        connection.connect();
        //获得服务器响应的数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String lineDate = null;
        while((lineDate = bufferedReader.readLine()) != null) {
            buffer.append(lineDate);
        }
        bufferedReader.close();
        if(buffer.toString().indexOf("\"error_code\":0")>=0 ) {
            return true;
        }
        return false;
    }



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
     * 生成6位随机验证码
     * @return
     */
    public static String getRandomCode(){
        //生成一个6位0~9之间的随机字符串
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
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
