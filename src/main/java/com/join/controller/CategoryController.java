package com.join.controller;

import com.join.entity.Category;
import com.join.entity.Result;
import com.join.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2022/9/8 9:06
 */
@Api(tags = "分类API")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/allCategory")
    @ApiOperation(value = "查询所有的类型",httpMethod = "GET")
    public Result finAllCategory(){
        List<Category> categoryList = categoryService.findAllCategory();
        return Result.success(categoryList);
    }
}
