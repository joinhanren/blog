package com.join.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.join.entity.Result;
import com.join.entity.User;
import com.join.mapper.UserMapper;
import com.join.service.UploadFile;
import com.join.utils.QiniuUtil;
import com.join.utils.ThreadLocalUser;
import com.join.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author join
 * @Description
 * @date 2022/9/7 16:23
 */
@Service
public class UploadFileImpl implements UploadFile {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户上传头像
     *
     * @param uploadAvatar
     * @param fileName
     * @return
     */
    @Override
    @Transactional
    public Result uploadAvatar(MultipartFile uploadAvatar, String fileName) {
        QiniuUtil qiniuUtil = new QiniuUtil();
        Boolean upload = qiniuUtil.upload(uploadAvatar, fileName);
        if (upload){
            UserVo userVo = ThreadLocalUser.get();
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",userVo.getId());
            updateWrapper.set("avatar",fileName);
            userMapper.update(null,updateWrapper);
            return Result.success(QiniuUtil.url+fileName);
        }
        return Result.fail(400,"图片上传失败");
    }
}
