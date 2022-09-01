package com.join.service.impl;

import com.alibaba.fastjson.JSON;
import com.join.entity.Result;
import com.join.entity.User;
import com.join.service.UserService;
import com.join.utils.JWTUtil;
import com.join.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author join
 * @Description
 * @date 2022/8/29 19:36
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 根据token查询用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {
        /**
         * 校验token 是否合法
         * 1.是否为空
         * 2.解析是否成功
         * 3.redis中是否存在
         */
        if ("".equals(token)){
            return Result.fail(400,"token 不能为空");
        }
        if (JWTUtil.verification(token)==null){
            return Result.fail(400,"用户校验失败");
        }

        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        UserVo userVo = JSON.parseObject(userJson, UserVo.class);
        return Result.success(userVo);
    }
}
