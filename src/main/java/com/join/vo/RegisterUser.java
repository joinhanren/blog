package com.join.vo;

import lombok.Data;

/**
 * @author join
 * @Description
 * @date 2022/8/31 22:17
 */
@Data
public class RegisterUser {
    private String account;
    private String password;
    private String email;
    private String verifyCode;
}
