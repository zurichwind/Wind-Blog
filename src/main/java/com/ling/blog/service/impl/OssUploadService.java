package com.ling.blog.service.impl;


import com.ling.blog.service.UploadService;
import com.ling.blog.utils.ResponseResult;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/8/1  14:04
 */
@Service
@Data
public class OssUploadService implements UploadService {

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        return null;
    }
}
