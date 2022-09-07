package com.join.service;

import com.join.entity.Result;
import org.springframework.web.multipart.MultipartFile;



/**
 * @author join
 * @Description
 * @date 2022/9/7 16:21
 */
public interface UploadFile {
    /**
     * 用户上传头像
     * @param uploadAvatar
     * @param fileName
     * @return
     */
    public Result uploadAvatar(MultipartFile uploadAvatar,String fileName);
}
