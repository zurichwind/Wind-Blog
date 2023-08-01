package com.ling.blog.job;

import com.ling.blog.entity.Article;
import com.ling.blog.service.ArticleService;
import com.ling.blog.utils.RedisCache;
import io.swagger.models.auth.In;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/28  17:10
 */
@Component
public class UpdateViewCount {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/55 * * * * ?")//定时配置Cron表达式
    public void updateViewCount(){

        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        //此段有错，错在哪？
        /*List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());*/


        List<Article> articles = new ArrayList<Article>();
        for (Map.Entry<String, Integer> entry : viewCountMap.entrySet()) {
            // 获取键和值
            String k = entry.getKey();
            Integer v = entry.getValue();
            Article article =new Article();
            article.setId(Long.valueOf(k));
            article.setViewCount(v.longValue());
            articles.add(article);
        }

        //批量更新到数据库中
        articleService.updateBatchById(articles);
    }
}
