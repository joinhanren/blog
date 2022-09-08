package com.join.controller;

import com.join.entity.Result;
import com.join.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author join
 * @Description
 * @date 2022/9/8 21:28
 */
@Api("标签API")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/allTags")
    @ApiOperation(value = "查询所有的标签",httpMethod = "GET")
    public Result findAllTags(){
        return Result.success(tagService.findAllTags());
    }
}
