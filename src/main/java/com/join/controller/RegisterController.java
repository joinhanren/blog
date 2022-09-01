package com.join.controller;

import com.join.entity.Result;
import com.join.service.RegisterService;
import com.join.vo.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author join
 * @Description
 * @date 2022/8/29 20:40
 */
@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 用户注册
     *
     * @param
     * @return
     */
    @PostMapping("/register")
//    public Result register(@RequestParam("account") String account,
//                           @RequestParam("password") String password,
//                           @RequestParam("email") String email,
//                           @RequestParam("verifyCode") String verifyCode) {
    public Result register(@RequestBody RegisterUser registerUser){
        /**
         * 此处判断字符串的校验，不能为空，同时字符串中不能 带有空格
         */
        return registerService.register(registerUser);
    }

    /**
     * 验证用户是否存在
     *
     * @param account
     * @return
     */

    @GetMapping("/verifyUser")
    public Result verifyUser(@RequestParam("account") String account) {
        System.out.println(account);
        return registerService.verifyUser(account);
    }



}
