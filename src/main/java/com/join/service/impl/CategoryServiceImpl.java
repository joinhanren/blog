package com.join.service.impl;

import com.join.entity.Category;
import com.join.mapper.CategoryMapper;
import com.join.service.CategoryService;
import com.join.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2022/9/1 22:37
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据id查询文章类型
     * @param id
     * @return
     */
    @Override
    public CategoryVo findCategoryById(Long id) {
        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    /**
     * 查询所有类型
     * @return
     */
    @Override
    public List<Category> findAllCategory() {
        return categoryMapper.findAllCategory();
    }
}
