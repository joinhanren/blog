package com.join.service;

import com.join.entity.Result;
import com.join.entity.User;
import com.join.vo.RegisterUser;

/**
 * @author join
 * @Description
 * @date 2022/8/29 20:54
 */
public interface RegisterService {

    /**
     * 注册用户
     * @param registerUser
     * @return
     */

    public Result register(RegisterUser registerUser);

    /**
     * 验证用户是否存在
     * @param account
     * @return
     */
    public Result verifyUser(String account);
}
