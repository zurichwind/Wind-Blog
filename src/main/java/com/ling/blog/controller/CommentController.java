package com.ling.blog.controller;

import com.ling.blog.constants.SystemConstants;
import com.ling.blog.entity.Comment;
import com.ling.blog.service.CommentService;
import com.ling.blog.utils.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/26  20:30
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
       return  commentService.addComment(comment);
    }


}
