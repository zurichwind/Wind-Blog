package com.ling.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.blog.constants.SystemConstants;
import com.ling.blog.entity.Role;
import com.ling.blog.entity.RoleMenu;
import com.ling.blog.entity.RoleRequest;
import com.ling.blog.mapper.RoleMapper;
import com.ling.blog.service.RoleMenuService;
import com.ling.blog.service.RoleService;
import com.ling.blog.utils.BeanCopyUtils;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.vo.PageVo;
import com.ling.blog.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/8/1  14:15
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult getAllRole(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper();
        wrapper.like((!(roleName==""||roleName==null)),Role::getRoleName,roleName);
        wrapper.eq((!(status==""||status==null)),Role::getStatus,status);
        wrapper.orderByAsc(Role::getRoleSort);
        //分页
        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, wrapper);
        //转换至要的类型
        List<RoleVo> roleVo = page.getRecords().stream()
                .map(m->(BeanCopyUtils.copyBean(m, RoleVo.class)))
                .collect(Collectors.toList());
        //封装数据返回
        PageVo pageVo = new PageVo(roleVo,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleRequest roleRequest) {
        //先找到
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Role::getId,roleRequest.getRoleId());
        List<Role> list = list(wrapper);
        //改变值
        list.get(0).setStatus(roleRequest.getStatus());
        //修改
        update(list.get(0),wrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult insertRole(Role role) {
        //先插入role表
        save(role);
        //再插入role-menu表
        List<RoleMenu> collect = role.getMenuIds().stream()
                .map(m -> new RoleMenu(role.getId(), Long.valueOf(m)))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRole(Long id) {
        Role role = getById(id);
        return ResponseResult.okResult(role);
    }

    @Override
    public ResponseResult updateRole(Role role) {
        updateById(role);
        //更新role_menu表
        //先删除
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,role.getId());
        roleMenuService.remove(wrapper);
        //再插入
        List<RoleMenu> collect = role.getMenuIds().stream()
                .map(m -> new RoleMenu(role.getId(), Long.valueOf(m)))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        removeById(id);
        //删除role_menu关系
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        roleMenuService.remove(wrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> list = list(wrapper);
        return ResponseResult.okResult(list);
    }
}
