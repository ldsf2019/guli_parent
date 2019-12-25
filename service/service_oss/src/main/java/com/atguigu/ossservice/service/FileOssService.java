package com.atguigu.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileOssService {
    //MultipartFile 用于接收二进制文件
    String uploadFileOss(MultipartFile file);
}
