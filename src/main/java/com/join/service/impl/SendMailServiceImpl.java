package com.join.service.impl;

import com.join.entity.Result;
import com.join.service.SendMailService;
import com.join.utils.CreateMailUtil;
import com.join.utils.VerifyInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * @author join
 * @Description
 * @date 2022/8/31 8:57
 */
@Service
@Slf4j
public class SendMailServiceImpl implements SendMailService {
    @Value("${spring.mail.username}")
    private String sendMailer;
    //注入邮件工具类
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送验证码
     *
     * @param email
     * @return
     */
    @Override
    public Result sendVerifyCode(String email) {
        CreateMailUtil createMailUtil = new CreateMailUtil();
        System.out.println(sendMailer);
        createMailUtil.setSendMailer(sendMailer);
        String code = VerifyInfoUtil.createVerifyCode();
        System.out.println("验证码===========>"+code);
        SimpleMailMessage MailMessage = createMailUtil.createSimpleMailMessage(email, code);
        try {
            javaMailSender.send(MailMessage);
        } catch (Exception e) {
            log.info("邮件发送失败");
            e.printStackTrace();
            return Result.fail(400, "验证码发送失败");
        }

        redisTemplate.opsForValue().set(email, code, 2, TimeUnit.MINUTES);

        return Result.success(null);
    }
}
