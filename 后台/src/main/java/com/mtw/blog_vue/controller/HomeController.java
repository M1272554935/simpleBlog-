package com.mtw.blog_vue.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtw.blog_vue.constant.WebConst;
import com.mtw.blog_vue.mapper.A_LJoinMapper;
import com.mtw.blog_vue.pojo.ArticleDomain;
import com.mtw.blog_vue.pojo.dto.Art_LabDto;
import com.mtw.blog_vue.service.ArticleService;
import com.mtw.blog_vue.service.LabelService;
import com.mtw.blog_vue.utils.CacheManager;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mtw.blog_vue.constant.WebConst.dayKey;


@RestController
public class HomeController {

    @Autowired
    ArticleService articleService;

    @Autowired
    LabelService labelService;

    @Autowired
    A_LJoinMapper aLJoinMapper; //不知道为什么一直报红

    CacheManager manager = CacheManager.getInstance();



    /**
     * 主页
     * @param
     * @param
     * @return
     */
    @GetMapping("/index")
    public Page<ArticleDomain> indexPage(
            @ApiParam(name = "page",value = "文章开始页数")
            @RequestParam(name = "page"  ,defaultValue = "1" ,required = false) int page,
            @ApiParam(name = "pageSize",value = "分页大小")
            @RequestParam(name = "pageSize"  ,defaultValue = "6",required = false) int pageSize
    ){

        Page<ArticleDomain> recentArt = articleService.getRecentArt(page, pageSize);

        return recentArt;
    }

    /**
     * 文章列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Page<ArticleDomain> listPage(
            @ApiParam(name = "currentPage",value = "文章开始页数")
            @RequestParam(name = "currentPage"  ,defaultValue = "1" ,required = false) int currentPage,
            @ApiParam(name = "pageSize",value = "分页大小")
            @RequestParam(name = "pageSize"  ,defaultValue = "1",required = false) int pageSize
    ){

        Page<ArticleDomain> recentArt = articleService.getRecentArt(currentPage, pageSize);

        return recentArt;
    }

    /**
     * 侧边栏数据，点击榜前8的文章
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/sidebarList")
    public Page<ArticleDomain> getSidebar(
            @ApiParam(name = "currentPage",value = "文章开始页数")
            @RequestParam(name = "currentPage"  ,defaultValue = "1" ,required = false) int currentPage,
            @ApiParam(name = "pageSize",value = "分页大小")
            @RequestParam(name = "pageSize"  ,defaultValue = "8",required = false) int pageSize
    ){

        Page<ArticleDomain> articleByHit = articleService.getArticleByHit(currentPage, pageSize);


        return articleByHit;
    }


    /**
     * 搜索
     *
     */
    @GetMapping("/search")
    public Page<ArticleDomain> getSearch(
            @ApiParam(name = "keyword",value = "关键字")
            @RequestParam(name = "keyword" ) String keyword,
            @ApiParam(name = "currentPage",value = "文章开始页数")
            @RequestParam(name = "currentPage"  ,defaultValue = "1" ,required = false) int currentPage,
            @ApiParam(name = "pageSize",value = "分页大小")
            @RequestParam(name = "pageSize"  ,defaultValue = "10",required = false) int pageSize
    ){

        Page<ArticleDomain> page = articleService.searchArticle(keyword, currentPage, pageSize);


        return page;
    }

    /**
     * 文章单页
     *
     */
    @GetMapping("/page")
    public HashMap<String, Object> getPage(
            @ApiParam(name = "aid",value = "文章主键")
            @RequestParam(name = "aid" ) int aid
    ){
        HashMap<String, Object> map = new HashMap<>();
        ArticleDomain article = articleService.getArticleById(aid);
        map.put("article",article);
        //查询标签
        List<Art_LabDto> list = aLJoinMapper.getList(aid);
        ArrayList<String> labels = new ArrayList<>();
        for (Art_LabDto lab:list) {
            labels.add(lab.getLName());
        }
        map.put("label",labels);
        updateArticleHit(article.getAid(),article.getHits());

        return map;
    }

    /**
     * 获取分类的文章
     * @param currentPage
     * @param pageSize
     * @param type
     * @return
     */
    public Page<ArticleDomain> getArticle2Type(int currentPage,int pageSize,String type){


        Page<ArticleDomain> articleOf = articleService
            .getArticleOfType(currentPage, pageSize, type);
       return articleOf;


    }
    /**
     * 文章分类页 (Although it's not good to use Chinese, I still want to use it)
     * @return
     */
    @GetMapping("/articleType")
    public HashMap<String, Object> getArticleType(
            @ApiParam(name = "currentPage",value = "文章开始页数")
            @RequestParam(name = "currentPage"  ,defaultValue = "1" ,required = false) int currentPage,
            @ApiParam(name = "pageSize",value = "分页大小")
            @RequestParam(name = "pageSize"  ,defaultValue = "6",required = false) int pageSize
    ){
        //type = ocean
        Page<ArticleDomain> ocean = getArticle2Type(currentPage, pageSize, "海洋");
        //type = technology(zhong wen shi ji shu)
        Page<ArticleDomain> it = getArticle2Type(currentPage, pageSize, "技术");
        //type = essay(zhong wen shi sui bi)
        Page<ArticleDomain> essay = getArticle2Type(currentPage, pageSize, "随笔");

        HashMap<String, Object> map = new HashMap<>();
        map.put("it",it);
        map.put("ocean",ocean);
        map.put("essay",essay);

        return map;
    }

    /**
     * 文章分类列表
     * @param currentPage
     * @param pageSize
     * @param type
     * @return
     */
    @GetMapping("/articleTypeList")
    public Page<ArticleDomain> getArticleTypeList(
            @ApiParam(name = "currentPage",value = "文章开始页数")
            @RequestParam(name = "currentPage"  ,defaultValue = "1" ,required = false) int currentPage,
            @ApiParam(name = "pageSize",value = "分页大小")
            @RequestParam(name = "pageSize"  ,defaultValue = "15",required = false) int pageSize,
            @RequestParam(name = "articleType") String type

    ){
        if (type.equals("it")){
            Page<ArticleDomain> it = getArticle2Type(currentPage, pageSize, "技术");
            return it;
        }
        if (type.equals("ocean")){
            Page<ArticleDomain> ocean = getArticle2Type(currentPage, pageSize, "海洋");
            return ocean;
        }
        if (type.equals("essay")){
            Page<ArticleDomain> essay = getArticle2Type(currentPage, pageSize, "随笔");
            return essay;
        }

        return null;
    }

    /**
     * 更新文章的点击率
     */
    private void updateArticleHit(Integer aid, Integer ArtHit) {

        CacheManager cache = CacheManager.getInstance();

        String aKey = "article"+aid;
        //更新当天的点击
        Integer dayV = cache.get(dayKey);
        cache.put(dayKey,dayV+1);
        //更新文章的点击数
        Integer cacheHit = cache.get(aKey);
        cacheHit = 0 == cacheHit ? 1 :cacheHit + 1;
        if (cacheHit == 10){

            ArticleDomain articleDomain = new ArticleDomain();
            articleDomain.setHits(ArtHit+cacheHit);
            articleDomain.setAid(aid);
            articleService.updateArticle(articleDomain);

            cache.put(aKey,0);
        }else {
            cache.put(aKey,cacheHit);
        }

    }
}
