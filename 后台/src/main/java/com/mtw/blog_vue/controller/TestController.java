package com.mtw.blog_vue.controller;

import com.mtw.blog_vue.mapper.ArticleMapper;
import com.mtw.blog_vue.pojo.ArticleDomain;
import com.mtw.blog_vue.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    ArticleService articleService;


    @Autowired
    ArticleMapper articleMapper;



    //@GetMapping("/list")
    public HashMap<String,Object> getList(){

        HashMap<String,Object> result = new HashMap<>();
        List<ArticleDomain> articleDomains = articleMapper.selectList(null);
        result.put("articleList",articleDomains);

        return result;
    }

    //@GetMapping("/page")
    public HashMap<String,Object> getArticle(@RequestParam(value = "aid") int aid){

        HashMap<String,Object> result = new HashMap<>();
        ArticleDomain articleDomain = articleMapper.selectById(aid);
        result.put("article",articleDomain);

        return result;
    }

    //@GetMapping("/test")
    public ArticleDomain get1(){

        return articleService.getArticleById(1);
    }


}
