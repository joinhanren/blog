package com.join.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.join.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author join
 * @Description
 * @date 2022/8/25 22:22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
