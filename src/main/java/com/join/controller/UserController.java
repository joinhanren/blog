package com.join.controller;

import com.join.annotation.LogAnnotation;
import com.join.entity.Result;
import com.join.params.PWD;
import com.join.service.UploadFile;
import com.join.service.UserService;
import com.join.utils.Encryption;
import com.join.vo.UserVo;
import io.swagger.annotations.*;
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
@Api(tags = "用户API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadFile uploadFile;

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */

    @PostMapping("/currentUser")
    @ApiOperation(value = "获取当前用户", httpMethod = "POST")
    public Result currentUser(@ApiParam(name = "token", value = "令牌")
                              @RequestHeader("Authorization") String token) {
        return userService.findUserByToken(token);
    }


    @PostMapping("/uploadAvatar")
    @LogAnnotation
    @ApiOperation(value = "用户头像的上传", httpMethod = "POST")
    public Result uploadAvatar(@ApiParam(name = "uploadAvatar", value = "上传的头像图片")
                               @RequestParam("uploadAvatar") MultipartFile uploadAvatar) {
        /**
         * 获取源文件名
         */
        String originalFilename = uploadAvatar.getOriginalFilename();
        /**
         * 生成唯一文件名称
         */
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        return uploadFile.uploadAvatar(uploadAvatar, fileName);
    }


    @ApiResponses
            ({
                    @ApiResponse(code = 400, message = "更新失败！"),
                    @ApiResponse(code = 200, message = "更新成功！")
            })
    @PostMapping("/changeInfo")
    @LogAnnotation
    @ApiOperation(value = "修改用户信息", httpMethod = "POST")
    public Result changeUserInfo(@ApiParam(name = "user", value = "用户基本信息") @RequestBody UserVo userVo) {
        if (StringUtils.isNotEmpty(userVo.getNickname())
                && StringUtils.isNotEmpty(userVo.getPhone())
                && StringUtils.isNotEmpty(userVo.getSex())
                && StringUtils.isNotEmpty(userVo.getSex())) {

            return userService.changeUserInfo(userVo);
        }
        return Result.fail(400, "不允许为空");
    }


    @PostMapping("/revisePassword")
    @LogAnnotation
    @ApiOperation(value = "修改密码", httpMethod = "POST")
    public Result revisePassword(@ApiParam(name = "pwd", value = "新密码和旧密码") @RequestBody PWD pwd) {
        if (StringUtils.isNotEmpty(pwd.getOldPassword()) && StringUtils.isNotEmpty(pwd.getNewPassword())) {
            pwd.setOldPassword(Encryption.MD5Encryption(pwd.getOldPassword()));
            pwd.setNewPassword(Encryption.MD5Encryption(pwd.getNewPassword()));
            return userService.revisePassword(pwd);
        }
        return Result.fail(400, "输入不能为空！");
    }


}
