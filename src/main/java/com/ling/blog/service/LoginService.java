package com.ling.blog.service;


import com.ling.blog.entity.User;
import com.ling.blog.utils.ResponseResult;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();

}
