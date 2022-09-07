package com.join.controller;

import com.join.annotation.LogAnnotation;
import com.join.entity.Result;
import com.join.service.UploadFile;
import com.join.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author join
 * @Description
 * @date 2022/8/29 19:34
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadFile uploadFile;

    /**
     *获取用户信息
     * @param token
     * @return
     */

    @PostMapping("/currentUser")
    @ApiOperation(value = "获取当前用户",httpMethod = "POST")
    public Result currentUser(@ApiParam(name = "token",value = "令牌")
                                  @RequestHeader("Authorization") String token) {
        return userService.findUserByToken(token);
    }


    @PostMapping("/uploadAvatar")
    @LogAnnotation
    @ApiOperation(value = "用户头像的上传",httpMethod = "POST")
    public Result uploadAvatar(@ApiParam(name = "uploadAvatar",value = "上传的头像图片")
                                   @RequestParam("uploadAvatar") MultipartFile uploadAvatar){
        /**
         * 获取源文件名
         */
        String originalFilename = uploadAvatar.getOriginalFilename();
        /**
         * 生成唯一文件名称
         */
        String fileName= UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        return uploadFile.uploadAvatar(uploadAvatar,fileName);
    }

}
