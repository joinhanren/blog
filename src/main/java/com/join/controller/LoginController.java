package com.join.controller;

import com.join.entity.Result;
import com.join.params.LoginParams;
import com.join.service.LoginService;
import com.join.utils.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author join
 * @Description
 * @date 2022/8/25 21:59
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param loginParams
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if ("".equals(account) || "".equals(password)) {
            return Result.fail(400, "参数不合法");
        }
        //md5加密
        String secret = Encryption.MD5Encryption(password);
        loginParams.setPassword(secret);
        return loginService.login(loginParams);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */

    @GetMapping("/loginOut")
    public Result loginOut(@RequestHeader("Authorization") String token) {
        return loginService.loginOut(token);
    }

}
