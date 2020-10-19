package com.mtw.blog_vue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtw.blog_vue.mapper.PictureMapper;
import com.mtw.blog_vue.pojo.PictureDomain;
import com.mtw.blog_vue.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PictureServiceImpl implements PictureService {


    @Autowired
    PictureMapper pictureMapper;

    @Override
    public void addPicture(PictureDomain picture) {

        pictureMapper.insert(picture);

    }

    @Override
    public void deletePicture(Integer pid) {

        pictureMapper.deleteById(pid);

    }

    @Override
    public void deletePictureByAid(Integer aid) {
        QueryWrapper<PictureDomain> wrapper = new QueryWrapper<>();
        wrapper.eq("aid",aid);

        pictureMapper.delete(wrapper);

    }

    @Override
    public void updatePicture(PictureDomain picture) {
            pictureMapper.updateById(picture);
    }

    @Override
    public PictureDomain getPictureById(Integer pid) {
        return pictureMapper.selectById(pid);
    }

    @Override
    public PictureDomain getPictureByUid(Integer uid) {
        return null;
    }

    @Override
    public PictureDomain getPictureByName(String pname) {
        QueryWrapper<PictureDomain> wrapper = new QueryWrapper<>();
        wrapper.eq("pname",pname);
        PictureDomain pictureDomain = pictureMapper.selectOne(wrapper);
        return pictureDomain;
    }


}
