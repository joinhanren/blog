package com.join.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.join.entity.Result;
import com.join.entity.User;
import com.join.mapper.RegisterMapper;
import com.join.service.RegisterService;
import com.join.utils.Encryption;
import com.join.vo.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author join
 * @Description
 * @date 2022/8/29 20:58
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 注册用户
     * @param registerUser
     * @return
     */
    @Transactional
    @Override
    public Result register(RegisterUser registerUser) {
        String account = registerUser.getAccount();
        Result result = this.verifyUser(account);
        if (result.getCode() != 200) {
            return result;
        }
        String email = registerUser.getEmail();
        if (redisTemplate.opsForValue().get(email).equals(registerUser.getVerifyCode())) {
            User user = new User();
            user.setEmail(email);
            user.setAccount(account);
            user.setCreateDate(LocalDateTime.now());
            user.setPassword(Encryption.MD5Encryption(registerUser.getPassword()));
            user.setLastLogin(LocalDateTime.now());
            user.setNickname("游客123");
            user.setAvatar("default.png");
            registerMapper.insert(user);
            return Result.success(null);
        } else {
            return Result.fail(400, "");
        }
    }

    /**
     * 验证用户是否存在
     *
     * @param account
     * @return
     */
    @Override
    public Result verifyUser(String account) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account);
        queryWrapper.select(User::getAccount);
        queryWrapper.last("limit 1");
        User user = registerMapper.selectOne(queryWrapper);
        if (user != null) {
            return Result.fail(400, "用户名已存在");
        }
        return Result.success("用户合法");
    }
}
