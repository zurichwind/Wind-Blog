package com.ling.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.blog.entity.Category;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.vo.CategoryVo;
import org.springframework.stereotype.Service;


import java.util.List;


public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();


    ResponseResult pageList(Long pageNum, Long pageSize, String name, String status);

    ResponseResult insertCategory(Category category);

    ResponseResult selectCategory(Long id);

    ResponseResult updateCategory(Category category);

    ResponseResult deleteCategory(Long id);

}
