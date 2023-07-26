package com.ling.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
@Accessors(chain = true)
public class Menu {
    @TableId
    private Long id;

    private String menuName;
    private Long parentId;
    private Integer orderNum;
    private String path;
    private String component;
    private Integer isFrame;

    private String visible;

    private String menuType;
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;
    private String delFlag;

    private String status;
    private String icon;
    private String perms;
    private String remark;

}
