package com.ling.blog.handler;

import com.ling.blog.exception.SystemException;
import com.ling.blog.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/26  19:20
 */
@Slf4j
@RestController
public class GlobalExceptionHandler {


    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {
        //打印异常信息
        log.error("出现了异常! {}" , e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(SystemException e) {
        //打印异常信息
        log.error("出现了异常！ {}", e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

}
