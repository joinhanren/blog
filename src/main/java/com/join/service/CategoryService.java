package com.join.service;

import com.join.entity.Category;
import com.join.vo.CategoryVo;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2022/9/1 22:37
 */
public interface CategoryService {

    /**
     * 根据id 查询文章类型
     * @param id
     * @return
     */
    public CategoryVo findCategoryById(Long id);

    public List<Category> findAllCategory();
}
