package com.mtw.blog_vue.OOSUtil;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.mtw.blog_vue.constant.WebConst;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Date;

public class AliyunUtils {



    public OSSClient getOSSClient(){
        return new OSSClient(WebConst.MY_ENDPOINT,WebConst.MY_ACCESS_KEY_ID, WebConst.MY_ACCESS_KEY_SECRET);
    }

    public String picOSS(MultipartFile uploadFile , String filename) throws Exception {

        // 创建OSSClient实例
        OSSClient ossClient = this.getOSSClient();
        // 上传
        //需要一个输入流
        PutObjectResult putObjectResult = ossClient.putObject(WebConst.MY_BACKET_NAME, filename, new ByteArrayInputStream(uploadFile.getBytes()));
        // 关闭client
        String eTag = putObjectResult.getETag();

        ossClient.shutdown();
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(WebConst.MY_BACKET_NAME, filename, expiration).toString();
        return url;
    }

}
