package com.ling.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.blog.entity.Menu;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.vo.MenuVo;


import java.util.List;

public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult menuList(String status, String menuName);

    ResponseResult InsertList(Menu menu);

    ResponseResult selectById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long id);

    ResponseResult treeselect();

    ResponseResult rolMenuTreeSelect(Long id);

}
