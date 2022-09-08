package com.join.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.join.entity.Result;
import com.join.entity.User;
import com.join.mapper.UserMapper;
import com.join.params.PWD;
import com.join.service.UserService;
import com.join.utils.JWTUtil;
import com.join.utils.ThreadLocalUser;
import com.join.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author join
 * @Description
 * @date 2022/8/29 19:36
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserMapper userMapper;

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
        if ("".equals(token)) {
            return Result.fail(400, "token 不能为空");
        }
        if (JWTUtil.verification(token) == null) {
            return Result.fail(400, "用户校验失败");
        }

        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        UserVo userVo = JSON.parseObject(userJson, UserVo.class);
        return Result.success(userVo);
    }

    /**
     * 修改用户信息
     *
     * @param userVo
     * @return
     */
    /*** TransactionAspectSupport  手动回滚事务 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result changeUserInfo(UserVo userVo) {
        try {
            LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper();
            lambdaUpdateWrapper.eq(User::getId, ThreadLocalUser.get().getId());
            lambdaUpdateWrapper.set(User::getBirthday, userVo.getBirthday())
                    .set(User::getNickname, userVo.getNickname())
                    .set(User::getPhone, userVo.getPhone())
                    .set(User::getSex, userVo.getSex());
            userMapper.update(null, lambdaUpdateWrapper);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(400, "更新失败！");
        }

        return Result.success("更新成功！");
    }

    /**
     * 修改密码
     *
     * @param pwd
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result revisePassword(PWD pwd) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = null;
        try {
            lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(User::getId, ThreadLocalUser.get().getId())
                    .eq(User::getPassword, pwd.getOldPassword())
                    .set(User::getPassword, pwd.getNewPassword());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(400, "更新失败！");
        }
        return Result.success("更新成功！");
    }
}
