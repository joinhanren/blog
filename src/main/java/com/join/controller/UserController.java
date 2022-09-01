package com.join.controller;

import com.join.entity.Result;
import com.join.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author join
 * @Description
 * @date 2022/8/29 19:34
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *获取用户信息
     * @param token
     * @return
     */

    @PostMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        return userService.findUserByToken(token);
    }

}
