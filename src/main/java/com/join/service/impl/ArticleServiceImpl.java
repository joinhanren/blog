package com.join.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.join.entity.Article;
import com.join.mapper.UserMapper;
import com.join.params.PageParams;
import com.join.entity.Result;
import com.join.mapper.ArticleMapper;
import com.join.service.ArticleBodyService;
import com.join.service.ArticleService;
import com.join.service.CategoryService;
import com.join.service.ThreadService;
import com.join.vo.ArticleBodyVo;
import com.join.vo.ArticleVo;
import com.join.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private CategoryService categoryService;

    @Autowired
    private ThreadService threadService;

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
