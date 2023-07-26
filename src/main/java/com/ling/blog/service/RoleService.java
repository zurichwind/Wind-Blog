package com.ling.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.blog.entity.Role;
import com.ling.blog.entity.RoleRequest;
import com.ling.blog.utils.ResponseResult;


import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult getAllRole(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(RoleRequest roleRequest);

    ResponseResult insertRole(Role role);

    ResponseResult getRole(Long id);

    ResponseResult updateRole(Role role);

    ResponseResult deleteRole(Long id);

    ResponseResult listAllRole();

}
