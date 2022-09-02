package com.join.service.impl;

import com.join.entity.ArticleBody;
import com.join.mapper.ArticleBodyMapper;
import com.join.service.ArticleBodyService;
import com.join.vo.ArticleBodyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author join
 * @Description
 * @date 2022/9/1 22:22
 */
@Service
public class ArticleBodyServiceImpl implements ArticleBodyService {

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ArticleBodyVo findArticleBodyById(Long id) {
        ArticleBody articleBody = articleBodyMapper.selectById(id);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
