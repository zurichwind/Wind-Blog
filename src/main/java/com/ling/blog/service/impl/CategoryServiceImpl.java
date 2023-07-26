package com.ling.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.blog.constants.SystemConstants;
import com.ling.blog.entity.Article;
import com.ling.blog.entity.Category;
import com.ling.blog.mapper.CategoryMapper;
import com.ling.blog.service.ArticleService;
import com.ling.blog.service.CategoryService;
import com.ling.blog.utils.BeanCopyUtils;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/24  20:04
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired(required = false)
    private ArticleService articleService;


    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList  = articleService.list(articleWrapper);
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        List<Category> categories = listByIds(categoryIds);

        categories = categories.stream().filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categories);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        return null;
    }

    @Override
    public ResponseResult pageList(Long pageNum, Long pageSize, String name, String status) {
        return null;
    }

    @Override
    public ResponseResult insertCategory(Category category) {
        return null;
    }

    @Override
    public ResponseResult selectCategory(Long id) {
        return null;
    }

    @Override
    public ResponseResult updateCategory(Category category) {
        return null;
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        return null;
    }
}
