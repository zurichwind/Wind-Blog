package com.ling.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ling.blog.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<String> selectRoleKeyByUserId(Long id);

}
