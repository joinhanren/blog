package com.join.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.join.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2022/9/1 22:39
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询所有类型，返回文章类型列表
     * @return
     */
    @Select("select * from tb_category")
    public List<Category> findAllCategory();
}
