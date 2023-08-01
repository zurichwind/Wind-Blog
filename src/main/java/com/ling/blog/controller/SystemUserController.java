package com.ling.blog.controller;

import com.ling.blog.entity.User;
import com.ling.blog.service.UserService;
import com.ling.blog.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/8/1  13:39
 */
@RestController
@RequestMapping("/system/user")
public class SystemUserController {

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public ResponseResult UserList(Integer pageNum, Integer pageSize, String userName, String status, String phonenumber){
        return userService.userList(pageNum,pageSize,userName,status,phonenumber);
    }

    @PostMapping
    public ResponseResult insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }
    @DeleteMapping("{id}")
    private ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("{id}")
    private ResponseResult userDetail(@PathVariable Long id){
        return userService.userDetail(id);
    }

    @PutMapping
    private ResponseResult updateUser(@RequestBody User user){
        return userService.updateUserInfo(user);
    }





}
