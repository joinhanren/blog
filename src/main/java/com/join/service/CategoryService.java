package com.join.service;

import com.join.vo.CategoryVo;

/**
 * @author join
 * @Description
 * @date 2022/9/1 22:37
 */
public interface CategoryService {
    public CategoryVo findCategoryById(Long id);
}
