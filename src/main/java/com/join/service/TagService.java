package com.join.service;

import com.join.entity.Tag;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2022/9/8 21:21
 */
public interface TagService {
    /**
     * 查询所有的标签
     * @return
     */
    public List<Tag> findAllTags();
}
