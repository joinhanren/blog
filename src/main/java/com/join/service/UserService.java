package com.join.service;

import com.join.entity.Result;

/**
 * @author join
 * @Description
 * @date 2022/8/29 19:35
 */
public interface UserService {
    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */

    public Result findUserByToken(String token);
}
