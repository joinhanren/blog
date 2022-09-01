package com.join.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author join
 * @Description
 * @date 2022/8/29 20:11
 */
@Data
public class UserVo {
    private Long id;
    private String account;
    private Integer admin;
    private String avatar;
    private String email;
    private LocalDateTime lastLogin;
    private String nickname;
}
