package com.ling.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.blog.entity.User;
import com.ling.blog.mapper.UserMapper;
import com.ling.blog.service.UserService;
import com.ling.blog.utils.BeanCopyUtils;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.utils.SecurityUtils;
import com.ling.blog.vo.UserInfoVo;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/27  13:46
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        return null;
    }

    @Override
    public ResponseResult userList(Integer pageNum, Integer pageSize, String userName, String status, String phonenumber) {
        return null;
    }

    @Override
    public ResponseResult insertUser(User user) {
        return null;
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        return null;
    }

    @Override
    public ResponseResult userDetail(Long id) {
        return null;
    }
}
