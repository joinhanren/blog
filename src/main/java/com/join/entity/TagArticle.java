package com.join.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author join
 * @Description
 * @date 2022/9/8 21:46
 */
@Data
@TableName("tb_tag_article")
public class TagArticle {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long articleId;

    private Long tagId;

}
