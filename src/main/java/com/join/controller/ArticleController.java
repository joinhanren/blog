package com.join.controller;

import com.join.annotation.LogAnnotation;
import com.join.params.PageParams;
import com.join.entity.Result;
import com.join.service.ArticleService;
import com.join.vo.EditArticle;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
     *
     * @param pageParams
     * @return
     */
    @PostMapping("/list")
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }


    /**
     * 测试请求
     *
     * @return
     */
    @GetMapping("/haha")
    @LogAnnotation(module = "文章", operation = "获取文章列表")
    public String haha() {

        return "haha";
    }


    @GetMapping("/test")
    @LogAnnotation(module = "文章", operation = "获取文章列表")
    public String test(@RequestParam("s") String s) {
        return s;
    }


    /**
     * 查看文章的详情
     *
     * @param id
     * @return
     */
    @LogAnnotation
    @GetMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long id) {
        return articleService.findArticleById(id);
    }


    @ApiResponses({
            @ApiResponse(code = 200,message = "文章发布成功！"),
            @ApiResponse(code = 400,message = "文章发布失败！")
    })
    @PostMapping("/pushArticle")
    @LogAnnotation(module = "发布文章模块", operation = "")
    @ApiOperation(value = "发布文章", httpMethod = "POST")
    public Result pushArticle(@ApiParam(name = "editArticle", value = "编辑文章的数据")
                              @RequestBody EditArticle editArticle) {
        return articleService.pushArticle(editArticle);
    }

}
