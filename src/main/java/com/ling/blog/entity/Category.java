package com.ling.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_category")
public class Category {
    @TableId
    private Long id;

    private String name;

    private Long pid;

    private String description;

    private String status;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Integer delFlag;

}
