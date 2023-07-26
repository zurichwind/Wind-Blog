package com.ling.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.blog.dto.TagListDto;
import com.ling.blog.entity.Tag;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.vo.PageVo;
import com.ling.blog.vo.TagVo;

import java.util.List;

public interface TagService extends IService<Tag> {
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult insertTag(Tag tag);

    ResponseResult deleteTag(Long id);

    ResponseResult selectById(Long id);

    ResponseResult updateTag(TagVo tagVo);

    List<TagVo> listAllTag();

}
