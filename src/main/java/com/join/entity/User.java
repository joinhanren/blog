package com.join.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author join
 * @Description  用户实体类
 * @date 2022/8/25 18:06
 */
@Data
@TableName("tb_user")
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String account;
    private Integer admin;
    private String sex;
    private LocalDateTime birthday;
    private String avatar;
    private LocalDateTime createDate;
    @TableLogic
    private Integer deleted;
    private String email;
    private LocalDateTime lastLogin;
    private String phone;
    private String nickname;
    private String password;
    private String salt;
    private String status;
}
