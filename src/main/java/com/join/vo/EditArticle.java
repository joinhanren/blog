package com.join.vo;

import com.join.entity.Tag;
import lombok.Data;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2022/9/8 17:53
 */
@Data
public class EditArticle {
    private Long id;
    private String title;//文章标题
    private String summary;//简介
    private String content;
    private String contentHtml;
    private Long categoryId;//类型ID
    private List<Tag> tags;

}
