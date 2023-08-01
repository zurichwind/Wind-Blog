package com.ling.blog.controller;

import com.ling.blog.dto.TagListDto;
import com.ling.blog.entity.LoginUser;
import com.ling.blog.entity.Tag;
import com.ling.blog.service.TagService;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.utils.SecurityUtils;
import com.ling.blog.vo.PageVo;
import com.ling.blog.vo.TagVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/29  19:00
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    public ResponseResult insertTag(@RequestBody Tag tag){
        //通过token找到创建人
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long id = loginUser.getUser().getId();
        tag.setCreateBy(id);
        tag.setUpdateBy(id);
        //获取当前时间
        Date date = new Date();
        tag.setCreateTime(date);
        tag.setUpdateTime(date);
        //存到数据库中并返回
        return tagService.insertTag(tag);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("{id}")
    public ResponseResult getTag(@PathVariable("id") Long id){
        return tagService.selectById(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagVo tagVo){
        return tagService.updateTag(tagVo);
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }

}
