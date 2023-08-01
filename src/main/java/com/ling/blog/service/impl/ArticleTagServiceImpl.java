package com.ling.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.blog.entity.ArticleTag;
import com.ling.blog.mapper.ArticleTagMapper;
import com.ling.blog.service.ArticleTagService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/8/1  13:50
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper,ArticleTag> implements ArticleTagService {
    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<ArticleTag> gettags(Long id) {
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> articleTags = articleTagMapper.selectList(lambdaQueryWrapper);
        return articleTags;
    }
}
