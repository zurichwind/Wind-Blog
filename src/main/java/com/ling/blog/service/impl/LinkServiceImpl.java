package com.ling.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.blog.constants.SystemConstants;
import com.ling.blog.entity.Link;
import com.ling.blog.mapper.LinkMapper;
import com.ling.blog.service.LinkService;
import com.ling.blog.utils.BeanCopyUtils;
import com.ling.blog.utils.ResponseResult;
import com.ling.blog.vo.LinkVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/25  16:24
 */
@Service
public class LinkServiceImpl  extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成vo
        List<LinkVo> linkVos =  BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult linkList(Long pageNum, Long pageSize, String name, String status) {
        return null;
    }

    @Override
    public ResponseResult insertLink(Link link) {
        return null;
    }

    @Override
    public ResponseResult updateLink(Link link) {
        return null;
    }

    @Override
    public ResponseResult linkById(Long id) {
        return null;
    }

    @Override
    public ResponseResult deleteById(Long id) {
        return null;
    }
}
