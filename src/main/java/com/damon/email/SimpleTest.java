package com.damon.email;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * 邮件发送测试用例
 *
 * Created by Damon on 2017/3/29.
 */
public class SimpleTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String HOST = "smtp.163.com";

    public static String SEND_TO = "***@qq.com";

    public static String SEND_FROM = "***@163.com";

    public static String USERNAME = "***@163.com";

    public static String PASSWORD = "***";

    public JavaMailSenderImpl sender = null;

    @Before
    public void init() {
        sender = new JavaMailSenderImpl();

        // 设定mail server
        sender.setHost(HOST);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.timeout", "20000");
        sender.setJavaMailProperties(prop);
    }

    @Test
    public void testSendSimpleText() {
        // 建立邮件消息
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人 用数组发送多个邮件
        // String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
        // mailMessage.setTo(array);
        mailMessage.setTo(SEND_TO);
        mailMessage.setFrom(SEND_FROM);
        mailMessage.setSubject("我是邮件标题");
        mailMessage.setText(" 我是邮件文本！！ ");

        // 发送邮件
        sender.send(mailMessage);
        logger.info(" 邮件发送成功.. ");
    }

    @Test
    public void testSendHtmlText() throws Exception {
        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);

        // 设置收件人，寄件人
        messageHelper.setTo(SEND_TO);
        messageHelper.setFrom(SEND_FROM);
        messageHelper.setSubject("我是HTML邮件标题！");
        // true 表示启动HTML格式的邮件
        messageHelper.setText("<html><head></head><body><h1>hello!!spring html Mail</h1></body></html>", true);

        // 发送邮件
        sender.send(mailMessage);
        logger.info(" 邮件发送成功.. ");
    }

    @Test
    public void testSendTextWithFile() throws Exception {

        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = sender.createMimeMessage();
        // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用， multipart模式
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);

        // 设置收件人，寄件人
        messageHelper.setTo(SEND_TO);
        messageHelper.setFrom(SEND_FROM);
        messageHelper.setSubject("我是测试邮件中嵌套图片的标题!！");
        // true 表示启动HTML格式的邮件
        messageHelper.setText("<html><head></head><body><h1>hello!!spring image html mail</h1>" +
                        "<img src=\"cid:aaa\"/>" +
                        "</body></html>",
                true);

        FileSystemResource img = new FileSystemResource(new File("D:\\git指令.jpg"));
        messageHelper.addInline("aaa", img);

        // 发送邮件
        sender.send(mailMessage);
        logger.info(" 邮件发送成功.. ");
    }

    @Test
    public void testSendTextWithAttachment() throws Exception {
        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = sender.createMimeMessage();
        // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，multipart模式 为true时发送附件 可以设置html格式
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

        // 设置收件人，寄件人
        messageHelper.setTo(SEND_TO);
        messageHelper.setFrom(SEND_FROM);
        messageHelper.setSubject("我是测试邮件中上传附件标题!！");
        // true 表示启动HTML格式的邮件
        messageHelper.setText("<html><head></head><body><h1>你好：附件中有学习资料！</h1></body></html>", true);

        // 这里的方法调用和插入图片是不同的。
        messageHelper.addAttachment("git指令.jpg", new File("D:\\git指令.jpg"));

        // 发送邮件
        sender.send(mailMessage);
        logger.info(" 邮件发送成功.. ");
    }

}