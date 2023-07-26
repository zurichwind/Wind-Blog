package com.ling.blog.service;
import com.ling.blog.utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);

}
