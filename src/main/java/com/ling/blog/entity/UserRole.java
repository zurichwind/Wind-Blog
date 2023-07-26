package com.ling.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
@Accessors(chain = true)
public class UserRole {
    private Long userId;
    private Long roleId;
}
