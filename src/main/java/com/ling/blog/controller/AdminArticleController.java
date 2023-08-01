package com.ling.blog.controller;

import com.ling.blog.dto.AddArticleDto;
import com.ling.blog.entity.Article;
import com.ling.blog.service.ArticleService;
import com.ling.blog.utils.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/8/1  13:27
 */
@RestController
@RequestMapping("/content/article")
public class AdminArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult selectArticle(Integer pageNum, Integer pageSize, String title,String summary) {
        return articleService.selectArticle(pageNum,pageSize,title,summary);
    }


    @GetMapping("{id}")
    public ResponseResult selectArticleById(@PathVariable Long id){
        return articleService.selectArticleById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody Article article){
        return articleService.updateArticle(article);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        return articleService.deleteArticle(id);
    }

}
