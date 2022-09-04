package com.join.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.join.entity.Article;
import com.join.entity.Comment;
import com.join.mapper.ArticleMapper;
import com.join.service.ThreadService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author join
 * @Description
 * @date 2022/9/2 21:16
 */
@Service
public class ThreadServiceImpl implements ThreadService {

    /**
     * 采用线程池操作，来更新文章阅读次数
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    @Override
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article){
        /**
         * 该处可能存在问题，高并发的情况下，会出现文章已经被阅读了，但是更新阅读数失败了
         */
        int viewCount= article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCount+1);
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        lambdaUpdateWrapper.eq(Article::getId,article.getId());
        //乐观锁
        lambdaUpdateWrapper.eq(Article::getViewCounts,article.getViewCounts());
        articleMapper.update(articleUpdate,lambdaUpdateWrapper);
    }

    /**
     * 采用线程池操作，来更新评论点赞次数
     * @param mongoTemplate
     * @param commentID
     */
    @Async("taskExecutor")
    @Override
    public void addLike(MongoTemplate mongoTemplate,String commentID){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentID));

        Update update = new Update();
        update.inc("likes");
        mongoTemplate.updateFirst(query,update, Comment.class);
    }

}
