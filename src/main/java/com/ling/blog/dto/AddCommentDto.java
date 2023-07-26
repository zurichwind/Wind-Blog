package com.ling.blog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加评论dto")//用于描述实体类
public class AddCommentDto {
    private Long id;

    @ApiModelProperty(notes = "评论类型（0代表文章评论，1代表友链评论）")//用于描述实体的属性
    private String type;

    private Long articleId;
    private Long rootId;
    private String content;
    private Long toCommentUserId;
    private Long toCommentId;
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;
    private Integer delFlag;
}
