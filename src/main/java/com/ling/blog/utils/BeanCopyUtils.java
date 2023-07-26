package com.ling.blog.utils;


import com.ling.blog.entity.Article;
import com.ling.blog.service.ArticleService;
import com.ling.blog.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/24  16:40
 */
public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    public static <V> V  copyBean(Object source, Class<V> clazz) {
        // 创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            // 实现属性copy
            BeanUtils.copyProperties(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return  list.stream()
                .map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("wind");

        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
    }
}