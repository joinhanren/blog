package com.join.controller;

import com.join.params.PageParams;
import com.join.entity.Result;
import com.join.service.ArticleService;
import com.join.utils.ThreadLocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author join
 * @Description
 * @date 2022/8/25 16:24
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping("/list")
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    @GetMapping("/haha")
    public String haha(){
        System.out.println(ThreadLocalUser.get());
        return "dfadfsfsafsa";
    }


    /**
     * 查看文章的详情
     * @param id
     * @return
     */

    @GetMapping("/view/{id}")
    public Result findArticleById(Long id){
        return articleService.findArticleById(id);
    }

}
