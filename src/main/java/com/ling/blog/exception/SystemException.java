package com.ling.blog.exception;


import com.ling.blog.enums.AppHttpCodeEnum;

//自己定义的异常，继承RuntimeException
public class SystemException extends RuntimeException{

    private int code;
    private String msg;
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());  //super的是RuntimeException
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}