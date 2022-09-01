package com.join.utils;

import com.join.entity.MailRequest;
import com.join.service.impl.SendMailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.Assert;
import java.util.Date;

/**
 * @author join
 * @Description
 * @date 2022/8/31 9:59
 */
public class CreateMailUtil {

    private String sendMailer;

    private static final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);

    public void checkMail(MailRequest mailRequest) {
        Assert.notNull(mailRequest,"邮件请求不能为空");
        Assert.notNull(mailRequest.getSendTo(), "邮件收件人不能为空");
        Assert.notNull(mailRequest.getSubject(), "邮件主题不能为空");
        Assert.notNull(mailRequest.getText(), "邮件收件人不能为空");
    }

    public SimpleMailMessage createSimpleMailMessage(MailRequest mailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        checkMail(mailRequest);
        System.out.println("====================="+sendMailer);
        //邮件发件人
        message.setFrom(sendMailer);
        //邮件收件人 1或多个
        message.setTo(mailRequest.getSendTo().split(","));
        //邮件主题
        message.setSubject(mailRequest.getSubject());
        //邮件内容
        message.setText(mailRequest.getText());
        //邮件发送时间
        message.setSentDate(new Date());

        logger.info("发送邮件成功:{}->{}",sendMailer,mailRequest.getSendTo());
        return message;
    }

    public SimpleMailMessage createSimpleMailMessage(String receiveEmail,String code) {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSendTo(receiveEmail);
        mailRequest.setSubject("个人博客|验证码");
        mailRequest.setText(VerifyInfoUtil.createVerifyInfo(code));
        return createSimpleMailMessage(mailRequest);
    }

    public void setSendMailer(String sendMailer){
        this.sendMailer=sendMailer;
    }

//
//    public void sendHtmlMail(MailRequest mailRequest) {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        checkMail(mailRequest);
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message,true);
//            //邮件发件人
//            helper.setFrom(sendMailer);
//            //邮件收件人 1或多个
//            helper.setTo(mailRequest.getSendTo().split(","));
//            //邮件主题
//            helper.setSubject(mailRequest.getSubject());
//            //邮件内容
//            helper.setText(mailRequest.getText(),true);
//            //邮件发送时间
//            helper.setSentDate(new Date());
//
//            String filePath = mailRequest.getFilePath();
//            if (StringUtils.hasText(filePath)) {
//                FileSystemResource file = new FileSystemResource(new File(filePath));
//                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
//                helper.addAttachment(fileName,file);
//            }
//            javaMailSender.send(message);
//            logger.info("发送邮件成功:{}->{}",sendMailer,mailRequest.getSendTo());
//        } catch (MessagingException e) {
//            logger.error("发送邮件时发生异常！",e);
//        }
//    }

//
//    //注入邮件工具类
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Value("${spring.mail.username}")
//    private String sendMailer;
//
//    private static final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);
//
//    public void checkMail(MailRequest mailRequest) {
//        Assert.notNull(mailRequest,"邮件请求不能为空");
//        Assert.notNull(mailRequest.getSendTo(), "邮件收件人不能为空");
//        Assert.notNull(mailRequest.getSubject(), "邮件主题不能为空");
//        Assert.notNull(mailRequest.getText(), "邮件收件人不能为空");
//    }
//
//    @Override
//    public void sendSimpleMail(MailRequest mailRequest) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        checkMail(mailRequest);
//        //邮件发件人
//        message.setFrom(sendMailer);
//        //邮件收件人 1或多个
//        message.setTo(mailRequest.getSendTo().split(","));
//        //邮件主题
//        message.setSubject(mailRequest.getSubject());
//        //邮件内容
//        message.setText(mailRequest.getText());
//        //邮件发送时间
//        message.setSentDate(new Date());
//
//        javaMailSender.send(message);
//        logger.info("发送邮件成功:{}->{}",sendMailer,mailRequest.getSendTo());
//    }
//
//
//
//    @Override
//    public void sendHtmlMail(MailRequest mailRequest) {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        checkMail(mailRequest);
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message,true);
//            //邮件发件人
//            helper.setFrom(sendMailer);
//            //邮件收件人 1或多个
//            helper.setTo(mailRequest.getSendTo().split(","));
//            //邮件主题
//            helper.setSubject(mailRequest.getSubject());
//            //邮件内容
//            helper.setText(mailRequest.getText(),true);
//            //邮件发送时间
//            helper.setSentDate(new Date());
//
//            String filePath = mailRequest.getFilePath();
//            if (StringUtils.hasText(filePath)) {
//                FileSystemResource file = new FileSystemResource(new File(filePath));
//                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
//                helper.addAttachment(fileName,file);
//            }
//            javaMailSender.send(message);
//            logger.info("发送邮件成功:{}->{}",sendMailer,mailRequest.getSendTo());
//        } catch (MessagingException e) {
//            logger.error("发送邮件时发生异常！",e);
//        }
//    }

}
