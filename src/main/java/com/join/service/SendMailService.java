package com.join.service;

import com.join.entity.MailRequest;
import com.join.entity.Result;

/**
 * @author join
 * @Description
 * @date 2022/8/31 8:56
 */
public interface SendMailService {
    /**
     * 发送验证码
     * @param email
     * @return
     */
    public Result sendVerifyCode(String email);
}
