package com.ling.blog.controller;

import com.ling.blog.entity.Role;
import com.ling.blog.entity.RoleRequest;
import com.ling.blog.service.RoleService;
import com.ling.blog.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/8/1  13:35
 */
@RestController
@RequestMapping("system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    public ResponseResult AllRole(Integer pageNum, Integer pageSize, String RoleName, String status){
        return roleService.getAllRole(pageNum,pageSize,RoleName,status);
    }

    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleRequest roleRequest){
        return roleService.changeStatus(roleRequest);
    }

    @PostMapping
    public ResponseResult insertRole(@RequestBody Role role){
        return roleService.insertRole(role);
    }

    @GetMapping("{id}")
    public ResponseResult getRole(@PathVariable Long id){
        return roleService.getRole(id);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteRole(@PathVariable Long id){
        return roleService.deleteRole(id);
    }

    @GetMapping("listAllRole")
    public ResponseResult listAllRole(){
        return  roleService.listAllRole();
    }

}
