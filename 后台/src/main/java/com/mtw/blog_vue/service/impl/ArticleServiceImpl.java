package com.mtw.blog_vue.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtw.blog_vue.mapper.ArticleMapper;
import com.mtw.blog_vue.pojo.ArticleDomain;
import com.mtw.blog_vue.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    ArticleMapper articleMapper;

    /**
     * 新增并返回主键id
     * @param article
     * @return
     */
    @Override
    public int addArticle(ArticleDomain article) {

        int insert = articleMapper.insert(article);

        return insert;
    }

    @Override
    public void deleteArticleById(Integer aid) {
        if (aid != null){
            articleMapper.deleteById(aid);
        }
    }

    @Override
    public void updateArticle(ArticleDomain article) {

        if ( article != null){

            articleMapper.updateById(article);
        }

    }

    @Override
    public ArticleDomain getArticleById(Integer aid) {
        if (aid != null){

            return articleMapper.selectById(aid);
        }

        return null;
    }

    /**
     * 查询所有文章的数量
     * @return
     */
    @Override
    public Integer getArticleCount() {
        QueryWrapper<ArticleDomain> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("aid");
        wrapper.eq("state",1);
        Integer count = articleMapper.selectCount(wrapper);
        return count;
    }

    /**
     * 查询最新的文章
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<ArticleDomain> getRecentArt(int currentPage, int pageSize) {
        if ( currentPage <= 0 || pageSize <=0){
            return null;
        }

        Page<ArticleDomain> page = new Page<>(currentPage, pageSize);
        QueryWrapper<ArticleDomain> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("createTime");
        wrapper.eq("state",1);

        Page<ArticleDomain> articlePage = articleMapper.selectPage(page, wrapper);

        return articlePage;
    }

    /**
     * 查询所有
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<ArticleDomain> getAll(int currentPage, int pageSize) {

        Page<ArticleDomain> page = new Page<>(currentPage, pageSize);
        QueryWrapper<ArticleDomain> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("aid");
        wrapper.eq("state",1);

        Page<ArticleDomain> pages = articleMapper.selectPage(page, wrapper);

        return pages;
    }

    /**
     * 查询点击量高的文章
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<ArticleDomain> getArticleByHit(int currentPage, int pageSize) {

        if ( currentPage <= 0 || pageSize <=0){
            return null;
        }

        Page<ArticleDomain> page = new Page<>(currentPage, pageSize);
        QueryWrapper<ArticleDomain> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("hits");
        wrapper.eq("state",1);


        Page<ArticleDomain> articlePage = articleMapper.selectPage(page, wrapper);

        return articlePage;
    }

    /**
     *  搜索文章
     * @param param
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<ArticleDomain> searchArticle(String param, int currentPage, int pageSize) {
        if (param == null || currentPage <= 0 || pageSize <=0){
            return null;
        }

        Page<ArticleDomain> page = new Page<>(currentPage, pageSize);
        QueryWrapper<ArticleDomain> wrapper = new QueryWrapper<>();
        //是否搜索文章的内容？
        wrapper.like("title",param).or().like("info",param);
        wrapper.orderByDesc("createTime");
        wrapper.eq("state",1);

        Page<ArticleDomain> articlePage = articleMapper.selectPage(page, wrapper);

        return articlePage;
    }

    /**
     * 获取草稿箱的文章
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<ArticleDomain> getDraftArticle(int currentPage, int pageSize) {

        Page<ArticleDomain> page = new Page<>(currentPage, pageSize);
        QueryWrapper<ArticleDomain> wrapper = new QueryWrapper<>();
        wrapper.eq("state",0);


        Page<ArticleDomain> articlePage = articleMapper.selectPage(page, wrapper);

        return articlePage;

    }

    /**
     * 获取不同分类的文章
     * @param currentPage
     * @param pageSize
     * @param type
     * @return
     */
    @Override
    public Page<ArticleDomain> getArticleOfType(int currentPage, int pageSize,String type) {

        Page<ArticleDomain> pages = new Page<>(currentPage, pageSize);
        QueryWrapper<ArticleDomain> wrapper = new QueryWrapper<>();
        wrapper.eq("type",type);
        wrapper.eq("state",1);


        Page<ArticleDomain> articlePage = articleMapper.selectPage(pages, wrapper);

        return articlePage;

    }

    /**
     * 查询总点击数
     * @return
     */
    @Override
    public Integer getHitsCount(){
        QueryWrapper<ArticleDomain> wrapper = new QueryWrapper<>();
        wrapper.select("hits");
        List<ArticleDomain> list = articleMapper.selectList(wrapper);
        int count = 0;
        for (ArticleDomain a :list) {
            count += a.getHits();
        }

        return count;
    }
}
