package com.join.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.join.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author join
 * @Description
 * @date 2022/8/29 20:59
 */
@Mapper
public interface RegisterMapper extends BaseMapper<User> {
}
