package com.ling.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.blog.dto.AddArticleDto;
import com.ling.blog.entity.Article;
import com.ling.blog.utils.ResponseResult;
import org.springframework.stereotype.Service;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult selectArticle(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult selectArticleById(Long id);

    ResponseResult updateArticle(Article article);

    ResponseResult deleteArticle(Long id);

}