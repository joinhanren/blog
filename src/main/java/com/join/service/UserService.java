package com.join.service;

import com.join.entity.Result;
import com.join.params.PWD;
import com.join.vo.UserVo;


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

    /**
     * 修改用户信息
     * @param userVo
     * @return
     */
    public Result changeUserInfo( UserVo userVo);

    /**
     * 修改密码
     * @param pwd
     * @return
     */
    public Result revisePassword( PWD pwd);
}
