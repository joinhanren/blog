package com.join.service;

import com.join.params.PageParams;
import com.join.entity.Result;

/**
 * @author join
 * @Description
 * @date 2022/8/25 16:39
 */
public interface ArticleService {
    /**
     * 分页查询文章列表
     * @return
     */
    public Result listArticle(PageParams pageParams);

    /**
     * 通过文章的ID 查询文章的内容
     * @param id
     * @return
     */
    public Result findArticleById(Long id);
}
