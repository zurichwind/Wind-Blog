package com.ling.blog.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ling.blog.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/27  13:13
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        }catch (Exception e){
            e.printStackTrace();
            userId = -1L;//表示是自己创建的
        }
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("createBy",userId,metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("updateBy",userId,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("",SecurityUtils.getUserId(),metaObject);
    }
}
