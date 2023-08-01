package com.ling.blog.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.ling.blog.entity.Category;
import com.ling.blog.enums.AppHttpCodeEnum;
import com.ling.blog.service.CategoryService;
import com.ling.blog.utils.BeanCopyUtils;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.utils.WebUtils;
import com.ling.blog.vo.CategoryVo;
import com.ling.blog.vo.ExcelCategoryVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/8/1  13:31
 */
@RestController
@RequestMapping("/content/category")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    //导出至Excel
    @PreAuthorize("@ps.hasPermission('content:category:export')")//权限控制，调用PermissionService里的方法
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("分类导出")
                    .doWrite(excelCategoryVos);//TODO:此处报错，debug至net.sf.cglib.core这里无法往下
            System.out.println("测试之执行完成");

        } catch (Exception e) {
            //如果出现异常也要响应json
            response.reset();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult pagelist(Long pageNum,Long pageSize,String name,String status){
        return categoryService.pageList(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult insertCategory(@RequestBody Category category){
        return categoryService.insertCategory(category);
    }

    @GetMapping("{id}")
    public ResponseResult selectCategory(@PathVariable Long id){
        return categoryService.selectCategory(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }

}
