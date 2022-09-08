package com.join.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author join
 * @Description   标签实体类
 * @date 2022/9/8 8:29
 */
@Data
@TableName("tb_tag")
public class Tag {
    private Long id;
    private String name;
}
