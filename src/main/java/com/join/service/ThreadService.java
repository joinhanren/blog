package com.join.service;

import com.join.entity.Article;
import com.join.mapper.ArticleMapper;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author join
 * @Description
 * @date 2022/9/2 21:16
 */
public interface ThreadService {
    /**
     * 采用线程池操作，来更新文章阅读次数
     * @param articleMapper
     * @param article
     */
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article);

    /**
     * 采用线程池操作，来更新评论点赞次数
     * @param mongoTemplate
     * @param commentID
     */
    public void addLike(MongoTemplate mongoTemplate, String commentID);
}
