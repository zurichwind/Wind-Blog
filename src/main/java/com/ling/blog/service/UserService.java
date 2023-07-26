package com.ling.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.blog.entity.User;
import com.ling.blog.utils.ResponseResult;


public interface UserService extends IService< User> {


    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult userList(Integer pageNum, Integer pageSize, String userName, String status, String phonenumber);

    ResponseResult insertUser(User user);

    ResponseResult deleteUser(Long id);

    ResponseResult userDetail(Long id);

}
