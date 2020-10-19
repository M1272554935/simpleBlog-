package com.mtw.blog_vue.service;

import com.mtw.blog_vue.pojo.Article_Label;


import java.util.List;


public interface Ariticle_LabelService {

    //
    void addOne(Article_Label a);
    //
    void addByList(List<Article_Label> list);
    //
    void deleteById(Integer id);
    //根据文章id删除
    void deleteByAid(Integer aid);
    //
    void update(Article_Label a);
    //
    List<Article_Label> getByAid(Integer aid);

    Article_Label getOne(Article_Label la);

}
