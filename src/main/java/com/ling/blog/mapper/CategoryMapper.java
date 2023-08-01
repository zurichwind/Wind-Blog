package com.ling.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ling.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
