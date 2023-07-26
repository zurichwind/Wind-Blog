package com.ling.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.blog.entity.Link;
import com.ling.blog.utils.ResponseResult;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();

    ResponseResult linkList(Long pageNum, Long pageSize, String name, String status);

    ResponseResult insertLink(Link link);

    ResponseResult updateLink(Link link);

    ResponseResult linkById(Long id);

    ResponseResult deleteById(Long id);

}
