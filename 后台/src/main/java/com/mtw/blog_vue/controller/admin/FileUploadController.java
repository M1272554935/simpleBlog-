package com.mtw.blog_vue.controller.admin;

import com.mtw.blog_vue.OOSUtil.AliyunUtils;
import com.mtw.blog_vue.constant.WebConst;
import com.mtw.blog_vue.pojo.PictureDomain;
import com.mtw.blog_vue.service.PictureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class FileUploadController {

    private static final Logger LOGGER = LogManager.getLogger(FileUploadController.class);

    @Autowired
    PictureService pictureService;


    /**
     * 文章封面图片上传
     * @param file
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @PostMapping("/admin/upload")
    public HashMap<String, Object> fileUp(
            @RequestParam(value = "file") MultipartFile file){
        HashMap<String, Object> map = this.singleFileUpload(file);

        return map;

    }
    //文件上传
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @PostMapping("/admin/uploadImg")
    public HashMap<String, Object> singleFileUpload(
            @RequestParam(value = "image") MultipartFile file) {


        HashMap<String, Object> map = new HashMap<>();
        if (file != null){
            if (file.getSize() > WebConst.MAX_FILE_SIZE ){

                map.put("msg","文件大于7Mb，请重新选择!");
                return map;
            }

            try {

                String originalFilename = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String filename = uuid + originalFilename;

                //获取url
                AliyunUtils aliyunUtils = new AliyunUtils();
                String url = aliyunUtils.picOSS(file, filename);

                map.put("imgUrl",url);
                System.out.println(url);

                //存储图片信息
                PictureDomain pictureDomain = new PictureDomain();
                pictureDomain.setPname(filename);
                pictureDomain.setAddress(url);
                pictureService.addPicture(pictureDomain);



            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());

            }
        }
        return map;
    }
}
