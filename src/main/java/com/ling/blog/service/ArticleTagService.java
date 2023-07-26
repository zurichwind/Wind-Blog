package com.ling.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.blog.entity.ArticleTag;

import java.util.List;

public interface ArticleTagService extends IService<ArticleTag> {

    List<ArticleTag> gettags(Long id);

}
