package com.join.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.join.entity.Result;
import com.join.entity.User;
import com.join.mapper.UserMapper;
import com.join.params.LoginParams;
import com.join.service.LoginService;
import com.join.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author join
 * @Description
 * @date 2022/8/25 22:02
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Result login(LoginParams loginParams) {

        //定义查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,loginParams.getAccount());
        queryWrapper.eq(User::getPassword,loginParams.getPassword());
        queryWrapper.select(User::getId,User::getAccount,User::getNickname,User::getAvatar);
        queryWrapper.last("limit 1");

        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            return Result.fail(400,"用户名或密码错误");
        }
        String token = JWTUtil.createToken(user.getId());
        //redis存储token 并设置过期时间
        redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(user),30, TimeUnit.MINUTES);
        return Result.success(token);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */

    @Override
    public Result loginOut(String token) {
        Boolean delete = redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }
}
