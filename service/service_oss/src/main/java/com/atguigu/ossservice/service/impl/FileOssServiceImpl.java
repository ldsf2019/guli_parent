package com.atguigu.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.ossservice.service.FileOssService;
import com.atguigu.ossservice.utils.ConstantPropertiesUtil;
import com.atguigu.servicebase.handler.GuliException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileOssServiceImpl implements FileOssService {

    //上传文件到 oss
    @Override
    public String uploadFileOss(MultipartFile file) {
        //oss地址
        String endpoint = ConstantPropertiesUtil.END_POINT;

        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();

            //uuid加文件名，让文件名不重复
            String uuid = UUID.randomUUID().toString();
            fileName = uuid + fileName;

            //把文件做分类管理，按照日期进行存储
            //第二个参数：路径和文件名称
            //因为文件夹种带有 / ，所以在oss里创建的时候或根据 / 创建文件夹，实现按天分类
            String path = new DateTime().toString("yyyy/MM/dd");  // 2019/12/25,根据这样的格式解析

            fileName = path + "/" + fileName;

            //调用方法实现上传
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //拼接上传文件url
            // https://guli-filetest0826.oss-cn-beijing.aliyuncs.com/1.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            throw new GuliException(20001, "上传失败");
        }
    }
}
