package com.join.entity;

import lombok.Data;

/**
 * @author join
 * @Description
 * @date 2022/8/30 22:46
 */
@Data
public class ArticleBody {
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
