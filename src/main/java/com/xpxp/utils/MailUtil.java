package com.xpxp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailUtil {

    @Autowired
    private MailSender mailSender;

    public void sendVerificationCode(String toEmail, String code) {
        sendMessageToMail(mailSender, toEmail, code);
    }

    //注意这里一定要和Mail配置中的邮箱对应，不然无法成功发送
    public static final String FROM_EMAIL="318155349@qq.com";

    /**
     * 发送邮件的方法,传入的参数 邮件发送器，目标邮箱，关键验证码信息
     * @param mailSender
     * @param toEmail
     * @param code
     */
    public static void sendMessageToMail(MailSender mailSender, String toEmail, String code){
        SimpleMailMessage message = new SimpleMailMessage();        //消息体构造器
        message.setFrom(FROM_EMAIL);                        //发件人
        message.setTo(toEmail);                                       //收件人
        message.setSubject("mygo网站-用户找回密码");         //主题
        message.setText("您本次的验证码是：" +code + "\n有效期为2分钟");            //正文内容
        mailSender.send(message);
        System.out.println(code);
    }

}