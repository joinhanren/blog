package com.join.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.join.entity.Tag;
import com.join.mapper.TagMapper;
import com.join.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2022/9/8 21:22
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;
    /**
     * 查询所有的标签
     *
     * @return
     */
    @Override
    public List<Tag> findAllTags() {
        return tagMapper.selectList(new LambdaQueryWrapper<Tag>());
    }
}
