package com.join.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author join
 * @Description 密码加密工具
 * @date 2022/8/29 22:33
 */
public class Encryption {

    private static final String SALT = "*$token##x%2@";

    public static String MD5Encryption(String password){
        return DigestUtils.md5DigestAsHex((SALT + password + SALT).getBytes(StandardCharsets.UTF_8));
    }
}
