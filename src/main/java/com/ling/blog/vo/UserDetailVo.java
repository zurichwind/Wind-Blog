package com.ling.blog.vo;


import com.ling.blog.entity.Role;
import com.ling.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailVo {
    private List<String> roleIds;
    private List<Role> roles;
    private User user;
}
