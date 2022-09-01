package com.join.controller;

import com.join.entity.Result;
import com.join.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author join
 * @Description
 * @date 2022/8/31 9:16
 */
@RestController
public class SendMailController {
    @Autowired
    private SendMailService sendMailService;

    @GetMapping("/sendVerifyCode")
    public Result sendVerifyCode(@RequestParam("email") String email){
        return sendMailService.sendVerifyCode(email);
    }



}
