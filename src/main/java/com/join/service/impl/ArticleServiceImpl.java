package com.join.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.join.entity.*;
import com.join.mapper.ArticleBodyMapper;
import com.join.mapper.TagArticleMapper;
import com.join.mapper.UserMapper;
import com.join.mapper.impl.TagArticleMapperImpl;
import com.join.params.PageParams;
import com.join.mapper.ArticleMapper;
import com.join.service.ArticleBodyService;
import com.join.service.ArticleService;
import com.join.service.CategoryService;
import com.join.service.ThreadService;
import com.join.utils.ThreadLocalUser;
import com.join.vo.ArticleBodyVo;
import com.join.vo.ArticleVo;
import com.join.vo.CategoryVo;
import com.join.vo.EditArticle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author join
 * @Description
 * @date 2022/8/25 16:42
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleBodyService articleBodyService;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private TagArticleMapperImpl tagArticleMapperImpl;

    private static final Integer DEFAULT = 0x000;

    /**
     * 分页查询文章列表
     *
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> articlePage = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //是否置顶排序
//        queryWrapper.orderByDesc(Article::getWeight);
        //根据时间排序
        //order by create_date desc
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> page = articleMapper.selectPage(articlePage, queryWrapper);
        //获取list  ,不能直接返回
        List<Article> records = page.getRecords();
        List<ArticleVo> articleVoList = copyList(records);
        return Result.success(articleVoList);
    }

    /**
     * 通过文章的ID 查询文章的内容
     *
     * @param id
     * @return
     */
    @Override
    public Result findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = this.copy(article, true, true, true);
        /**
         * 采用线程池更新阅读数
         */
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    /**
     * 发布文章
     *
     * @param editArticle
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result pushArticle(EditArticle editArticle) {
        Long id = ThreadLocalUser.get().getId();
        Article article = new Article();
        article.setTitle(editArticle.getTitle());
        article.setSummary(editArticle.getSummary());
        article.setAuthorId(id);
        article.setCreateDate(LocalDateTime.now());
        article.setCategoryId(editArticle.getCategoryId());
        article.setViewCounts(DEFAULT);
        article.setCommentCounts(DEFAULT);
        article.setWeight(DEFAULT);
        try {
            articleMapper.insert(article);
            ArticleBody articleBody = new ArticleBody();
            articleBody.setContent(editArticle.getContent());
            articleBody.setContentHtml(editArticle.getContentHtml());
            articleBody.setArticleId(article.getId());
            this.articleBodyMapper.insert(articleBody);
            article.setBodyId(articleBody.getId());
            this.articleMapper.updateById(article);
            if (editArticle.getTags() != null) {
                List<TagArticle> list = new ArrayList<>();
                for (Tag tag : editArticle.getTags()) {
                    TagArticle tagArticle = new TagArticle();
                    tagArticle.setArticleId(article.getId());
                    tagArticle.setTagId(tag.getId());
                    list.add(tagArticle);
                }
                this.tagArticleMapperImpl.saveBatch(list);
            }
            Map<String, Long> map = new HashMap<>();
            map.put("id", article.getId());

            return Result.success(map);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(400, "发布文章失败！");
        }

    }


    private List<ArticleVo> copyList(List<Article> records) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        return articleVo;
    }

    private ArticleVo copy(Article article, boolean isAuthor, boolean articleBody, boolean isCategory) {
        ArticleVo articleVo = this.copy(article);
        BeanUtils.copyProperties(article, articleVo);
        /**
         * 查询结果是否附带作者昵称
         */
        if (isAuthor) {
            articleVo.setAuthor(userMapper.selectById(articleVo.getAuthorId()).getNickname());
        }
        /**
         * 是否附带博客详情
         */
        if (articleBody) {
            ArticleBodyVo body = articleBodyService.findArticleBodyById(articleVo.getBodyId());
            articleVo.setBody(body);
        }
        /**
         * 是否附带类型信息
         */
        if (isCategory) {
            Long categoryId = articleVo.getCategoryId();
            CategoryVo categoryVo = categoryService.findCategoryById(categoryId);
            articleVo.setCategory(categoryVo);
        }

        return articleVo;
    }


}
