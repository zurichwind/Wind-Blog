package com.ling.blog.service;


import com.ling.blog.entity.User;
import com.ling.blog.utils.ResponseResult;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();

}
