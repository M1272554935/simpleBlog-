package com.mtw.blog_vue.constant;

import org.springframework.stereotype.Component;

@Component
public class WebConst {

    //统计当天点击数的缓存的key
    public static final String dayKey = "dayKey";
    //统计当天发布的文章的缓存key
    public static final String articleKey = "articleKey";

    /**
     * 上传文件最大7M
     */
    public static Integer MAX_FILE_SIZE = 7340032;


    //阿里云API的内或外网域名
    public static String MY_ENDPOINT = "";
    //阿里云API的密钥Access Key ID
    public static String MY_ACCESS_KEY_ID = "";
    //阿里云API的密钥Access Key Secret
    public static String MY_ACCESS_KEY_SECRET = "";
    //阿里云API的bucket名称
    public static String MY_BACKET_NAME = "";
    //阿里云API的文件夹名称
    public static String MY_FOLDER = "";
    
}
