package com.atguigu.ossservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.ossservice.service.FileOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/eduoss/fileoss")
@Api(description = "oss文件上传")
@CrossOrigin
public class FileOssController {

    @Autowired
    private FileOssService fileOssService;

    @PostMapping("fileUpload")
    @ApiOperation(value = "通过oss上传讲师头像到阿里云")
    public R fileUploadOss(MultipartFile file){
        //得到上传过来的文件 MultipartFile
        //把获取文件上传阿里云 oss
        //返回上传文件在oss地址
        String s = fileOssService.uploadFileOss(file);
        return R.ok().data("url",s);
    }
}
