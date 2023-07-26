package com.ling.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
@Accessors(chain = true)
public class RoleMenu {
    private Long roleId;
    private Long MenuId;

}
