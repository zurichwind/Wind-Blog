package com.ling.blog.service.impl;

import com.ling.blog.service.UploadService;
import com.ling.blog.utils.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/27  19:22
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型或文件大小

        //如果判断通过上传文件到oss

        return null;
    }
}
