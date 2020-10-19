package com.mtw.blog_vue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtw.blog_vue.mapper.Article_LabelMapper;
import com.mtw.blog_vue.pojo.Article_Label;
import com.mtw.blog_vue.service.Ariticle_LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class Ariticle_LabelServiceImpl  implements Ariticle_LabelService {


    @Autowired
    Article_LabelMapper article_labelMapper;


    @Override
    public void addOne(Article_Label a) {
        if ( a != null){
            Article_Label one = getOne(a);
            if (one == null){
                article_labelMapper.insert(a);
            }
        }
    }

    @Override
    public void addByList(List<Article_Label> list) {
        for (Article_Label a:list) {
            this.addOne(a);
        }
    }

    @Override
    public void deleteById(Integer id) {
        article_labelMapper.deleteById(id);
    }

    @Override
    public void deleteByAid(Integer aid) {
        QueryWrapper<Article_Label> wrapper = new QueryWrapper<>();
        wrapper.eq("aid",aid);
        article_labelMapper.delete(wrapper);
    }

    @Override
    public void update(Article_Label a) {
        article_labelMapper.updateById(a);
    }

    @Override
    public Article_Label getOne(Article_Label la){
        QueryWrapper<Article_Label> wrapper = new QueryWrapper<>();
        wrapper.eq("aid",la.getAid());
        wrapper.eq("lid",la.getLid());
        return  article_labelMapper.selectOne(wrapper);

    }

    @Override
    public List<Article_Label> getByAid(Integer aid) {
        QueryWrapper<Article_Label> wrapper = new QueryWrapper<>();
        wrapper.eq("aid",aid);
        List<Article_Label> articleLabelList = article_labelMapper.selectList(wrapper);

        return articleLabelList;
    }

}
