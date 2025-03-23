package com.xpxp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * qq邮箱配置类
 */
@Configuration
public class MailConfig {
    private static final String USERNAME="318155349@qq.com";  // 指定发送邮件的账户
    private static final String PASSWORD="grccrlbrpggabgfd";  //指定发送邮箱的授权码

    /**
     * 配置MailSender
     * MailSender是一个接口，其一个实现类为JavaMailSenderImpl
     * @return
     */
    @Bean
    public MailSender mailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");  //指定用来发送邮件服务器的主机名
        mailSender.setPort(587);    //默认端口，标准的SMTP端口
        mailSender.setUsername(USERNAME);  //配置自己的qq邮箱
        mailSender.setPassword(PASSWORD);   //配置自己的qq邮箱发送授权码

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // 启用TLS
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return mailSender;
    }

}