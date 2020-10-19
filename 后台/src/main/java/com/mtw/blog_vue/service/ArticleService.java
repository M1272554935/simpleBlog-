package com.mtw.blog_vue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtw.blog_vue.pojo.ArticleDomain;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ArticleService {


    int addArticle(ArticleDomain article);

    void deleteArticleById(Integer aid);

    void updateArticle(ArticleDomain article);

    ArticleDomain getArticleById(Integer aid);

    Integer getArticleCount();

    Page<ArticleDomain> getRecentArt(int currentPage, int pageSize);

    Page<ArticleDomain> getArticleByHit(int currentPage, int pageSize);

    Page<ArticleDomain> searchArticle(String param,int currentPage, int pageSize);

    Page<ArticleDomain> getAll(int currentPage, int pageSize);

    Page<ArticleDomain> getDraftArticle(int currentPage, int pageSize);

    Page<ArticleDomain> getArticleOfType(int currentPage, int pageSize,String type);

    Integer getHitsCount();

}
