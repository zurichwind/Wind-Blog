package com.ling.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.blog.entity.Role;
import com.ling.blog.entity.User;
import com.ling.blog.entity.UserRole;
import com.ling.blog.enums.AppHttpCodeEnum;
import com.ling.blog.exception.SystemException;
import com.ling.blog.mapper.UserMapper;
import com.ling.blog.service.RoleService;
import com.ling.blog.service.UserRoleService;
import com.ling.blog.service.UserService;
import com.ling.blog.utils.BeanCopyUtils;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.utils.SecurityUtils;
import com.ling.blog.vo.PageVo;
import com.ling.blog.vo.UserDetailVo;
import com.ling.blog.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/27  13:46
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }

        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...

        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userList(Integer pageNum, Integer pageSize, String userName, String status, String phonenumber) {
        LambdaQueryWrapper<User> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.like(!(userName==""||userName==null),User::getUserName,userName);
        Wrapper.eq(!(phonenumber==""||phonenumber==null),User::getPhonenumber,phonenumber);
        Wrapper.eq(!(status==""||status==null),User::getStatus,status);
        //page封装
        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, Wrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult insertUser(User user) {
        if (user.getUserName()==null||user.getUserName()=="")
            return ResponseResult.errorResult(AppHttpCodeEnum.REQUIRE_USERNAME.getCode(),AppHttpCodeEnum.REQUIRE_USERNAME.getMsg());
        if (userNameExist(user.getUserName()))
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST.getCode(),AppHttpCodeEnum.USERNAME_EXIST.getMsg());
        if (phoneNumberExist(user.getPhonenumber()))
            return ResponseResult.errorResult(AppHttpCodeEnum.PHONENUMBER_EXIST.getCode(),AppHttpCodeEnum.PHONENUMBER_EXIST.getMsg());
        if (EmailExist(user.getEmail()))
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_EXIST.getCode(),AppHttpCodeEnum.EMAIL_EXIST.getMsg());
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入user数据库
        save(user);
        //关联角色表
        List<UserRole> userRoles = user.getRoleIds().stream().map(m -> new UserRole(user.getId(), Long.valueOf(m))).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        if (userId.equals(id))
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_DELETE_NOWUSER.getCode(),AppHttpCodeEnum.NOT_DELETE_NOWUSER.getMsg());
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userDetail(Long id) {
        UserDetailVo userDetailVo = new UserDetailVo();
        LambdaQueryWrapper<UserRole> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.eq(UserRole::getUserId,id);
        List<UserRole> role = userRoleService.list(Wrapper);
        List<String> roleCollect = role.stream().map(m -> m.getRoleId().toString()).collect(Collectors.toList());
        userDetailVo.setRoleIds(roleCollect);
        //再放入角色列表
        List<Role> roles = roleCollect.stream().map(m -> roleService.getById(Long.valueOf(m))).collect(Collectors.toList());
        userDetailVo.setRoles(roles);
        //放入user信息
        User user = getById(id);
        userDetailVo.setUser(user);
        return ResponseResult.okResult(userDetailVo);
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getNickName,nickName);
        return count(lambdaQueryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,userName);
        return count(lambdaQueryWrapper)>0;
    }
    private boolean phoneNumberExist(String phoneNumber) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhonenumber,phoneNumber);
        return count(lambdaQueryWrapper)>0;
    }
    private boolean EmailExist(String email) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail,email);
        return count(lambdaQueryWrapper)>0;
    }
}
