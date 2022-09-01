package com.join.service;

import com.join.entity.Result;
import com.join.params.LoginParams;

/**
 * @author join
 * @Description
 * @date 2022/8/25 22:01
 */
public interface LoginService {

    public Result login(LoginParams loginParams);
    public Result loginOut(String token);
}
