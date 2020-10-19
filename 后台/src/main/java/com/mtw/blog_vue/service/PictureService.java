package com.mtw.blog_vue.service;

import com.mtw.blog_vue.pojo.PictureDomain;
import org.springframework.stereotype.Service;


@Service
public interface PictureService {

    void addPicture(PictureDomain picture);

    void deletePicture(Integer pid);

    void deletePictureByAid(Integer aid);

    void updatePicture(PictureDomain picture);

    PictureDomain getPictureById(Integer pid);

    PictureDomain getPictureByUid(Integer uid);

    PictureDomain getPictureByName(String pname);


}
