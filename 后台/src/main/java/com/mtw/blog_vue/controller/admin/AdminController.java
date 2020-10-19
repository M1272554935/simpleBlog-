package com.mtw.blog_vue.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtw.blog_vue.constant.WebConst;
import com.mtw.blog_vue.pojo.ArticleDomain;
import com.mtw.blog_vue.pojo.Article_Label;
import com.mtw.blog_vue.pojo.LabelDomain;
import com.mtw.blog_vue.service.Ariticle_LabelService;
import com.mtw.blog_vue.service.ArticleService;
import com.mtw.blog_vue.service.LabelService;
import com.mtw.blog_vue.utils.CacheManager;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    ArticleService articleService;

    @Autowired
    LabelService labelService;

    @Autowired
    Ariticle_LabelService ariticle_labelService;

    /**
     * 获取所有的文章
     * @param page
     * @param pageSize
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)  //shiro角色访问
    @GetMapping("/admin/articleList")
    public Page<ArticleDomain> getAll(
            @ApiParam(name = "page",value = "文章开始页数")
            @RequestParam(name = "page"  ,defaultValue = "1" ,required = false) int page,
            @ApiParam(name = "pageSize",value = "分页大小")
            @RequestParam(name = "pageSize"  ,defaultValue = "15",required = false) int pageSize
    ){

        Page<ArticleDomain> all = articleService.getAll(page, pageSize);

        return all;
    }

    /**
     * 获取所有草稿
     * @param page
     * @param pageSize
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @GetMapping("/admin/draftList")
    public Page<ArticleDomain> getDraftList(
            @ApiParam(name = "page",value = "文章开始页数")
            @RequestParam(name = "page"  ,defaultValue = "1" ,required = false) int page,
            @ApiParam(name = "pageSize",value = "分页大小")
            @RequestParam(name = "pageSize"  ,defaultValue = "15",required = false) int pageSize
    ){

        Page<ArticleDomain> draftArticle = articleService.getDraftArticle(page, pageSize);

        return draftArticle;
    }


    /**
     * 文章发布页获取所有的标签
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @GetMapping("/admin/push")
    public HashMap<String,Object> pushArticlePage(){

        HashMap<String, Object> map = new HashMap<>();
        List<LabelDomain> allLabel = labelService.getAllLabel();
        ArrayList<String> labels = new ArrayList<>();
        for (LabelDomain labelDomain:allLabel) {
            labels.add(labelDomain.getLname());
        }
        map.put("label",labels);

        return map;
    }


    /**
     * 获取草稿
     * @param aid
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @GetMapping("/admin/draft")
    public HashMap<String,Object> getDraft(
            @RequestParam(name = "aid") int aid)
    {
        ArticleDomain articleById = articleService.getArticleById(aid);
        //获取文章的标签
        List<Article_Label> byAid = ariticle_labelService.getByAid(aid);
        ArrayList<String> labelList = new ArrayList<>();
        for (Article_Label al : byAid) {
            LabelDomain label = labelService.getLabelById(al.getLid());
            labelList.add(label.getLname());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("article",articleById);
        map.put("label",labelList);

        return map;
    }

    /**
     * 添加标签
     * @param lname
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @GetMapping("/admin/addLabel")
    public int addLabel(
            @RequestParam(name = "lname") String lname
    ){

        LabelDomain selectL = labelService.getByName(lname);
        if (selectL == null){
            LabelDomain labelDomain = new LabelDomain();
            labelDomain.setLname(lname);
            labelService.AddLabel(labelDomain);
            return 200;
        }

        return 403;
    }

    /**
     * 删除标签
     * @param lid
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @GetMapping("/admin/deleteLabel")
    public int deleteLabel(
            @RequestParam(name = "lid") int lid
    ){
        if (lid > 0){
            LabelDomain labelById = labelService.getLabelById(lid);
            if (labelById != null){
                labelService.deletLabelById(lid);
                return 200;
            }
        }
        return 400;
    }

    /**
     * 标签列表获取所有标签
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @GetMapping("/admin/label")
    public HashMap<String, Object> getAllLabel(){

        HashMap<String, Object> map = new HashMap<>();
        List<LabelDomain> allLabel = labelService.getAllLabel();
        map.put("label",allLabel);

        return map;
    }

    /**
     * 获取日浏览数、日新增文章、总浏览数、总文章
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @GetMapping("/admin/index")
    public HashMap<String, Object> getDayHit(){

        //日数据
        HashMap<String, Object> map = new HashMap<>();
        CacheManager manager = CacheManager.getInstance();
        Integer dayHit = manager.get(WebConst.dayKey);
        Integer dayArticle = manager.get(WebConst.articleKey);

        //总数据
        Integer articleCount = articleService.getArticleCount();
        Integer hitsCount = articleService.getHitsCount();

        map.put("dayHit",dayHit);
        map.put("dayArticle",dayArticle);
        map.put("articleCount",articleCount);
        map.put("hitsCount",hitsCount);

        return map;
    }



}
