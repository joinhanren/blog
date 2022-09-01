package com.join.entity;

import lombok.Data;

/**
 * @author join
 * @Description   文章分类表
 * @date 2022/8/30 22:52
 */
@Data
public class Category {
    private Long id;
    private String avatar;  //分类图标类型路径
    private String categoryName;//分类名称
    private String description;//分类的描述
}
