package com.ling.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.blog.constants.SystemConstants;
import com.ling.blog.dto.AddArticleDto;
import com.ling.blog.entity.Article;
import com.ling.blog.entity.ArticleTag;
import com.ling.blog.entity.Category;
import com.ling.blog.mapper.ArticleMapper;
import com.ling.blog.service.ArticleService;
import com.ling.blog.service.ArticleTagService;
import com.ling.blog.service.CategoryService;
import com.ling.blog.utils.BeanCopyUtils;
import com.ling.blog.utils.RedisCache;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.vo.*;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/24  15:08
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService  {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回，mybatisplus
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章(需求1)
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序(需求2)
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条(需求3)，mybatisplus
        Page<Article> page = new Page(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

        //bean拷贝,一些不用的字段不给出去
       /* List<HotArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(article, vo);
            articleVos.add(vo);
        }*/

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){

        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //上面只有查文章表没有categoryName

        //查询categoryName
        //articleId去查询articleName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        //再次封装至前端要求的page返回形式
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional //事务:原子性
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article); //保存到数据库中

        //获得表联系以存至连接表sg_article_tag中
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectArticle(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //查询条件模糊查询
        lambdaQueryWrapper.like(!(title=="" || title==null),Article::getTitle,title);
        lambdaQueryWrapper.like(!(summary=="" || summary==null),Article::getSummary,summary);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);
        List<Article> articles = page.getRecords();
        //封装结果至要求格式1
        List<ArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, ArticleVo.class);
        //封装page格式
        PageVo pageVo = new PageVo(articleVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult selectArticleById(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        if (!(viewCount==null)){
            article.setViewCount(viewCount.longValue());
        }

        //获取关联的tags
        List<ArticleTag> tags = articleTagService.gettags(id);
        //从tags中取出tag_id
        List<String> tagId = tags.stream()
                .map(tag ->tag.getTagId().toString())
                .collect(Collectors.toList());
        //装入
        article.setTags(tagId);

        return ResponseResult.okResult(article);
    }

    @Override
    public ResponseResult updateArticle(Article article) {
        //更新sg_article表
        updateById(article);
        //更新sg_article_tag表
        List<String> tags = article.getTags();
        //先删除
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(lambdaQueryWrapper);
        //再保存
        List<ArticleTag> collect = tags.stream()
                .map(tag -> new ArticleTag(article.getId(), Long.valueOf(tag)))
                .collect(Collectors.toList());
        articleTagService.saveBatch(collect);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}
