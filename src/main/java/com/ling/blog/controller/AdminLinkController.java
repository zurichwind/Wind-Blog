package com.ling.blog.controller;

import com.ling.blog.entity.Link;
import com.ling.blog.service.LinkService;
import com.ling.blog.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/8/1  13:34
 */
@RestController
@RequestMapping("/content/link")
public class AdminLinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("list")
    public ResponseResult linkList(Long pageNum, Long pageSize, String name, String status){
        return linkService.linkList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult insertLink(@RequestBody Link link){
        return linkService.insertLink(link);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        return linkService.updateLink(link);
    }

    @GetMapping("{id}")
    public ResponseResult linkById(@PathVariable Long id){
        return linkService.linkById(id);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        return linkService.deleteById(id);
    }

}
