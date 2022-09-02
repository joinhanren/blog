package com.join.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author join
 * @Description
 * @date 2022/8/25 17:16
 */
@Data
public class ArticleVo {
    private long id;
    private String title;
    private String summary;
    private int commentCounts;
    private int viewCounts;
    private long authorId;
    private long bodyId;
    private long categoryId;
    private LocalDateTime createDate;
    private int weight;

    private String author;

    private ArticleBodyVo body;

    private CategoryVo category;

}
