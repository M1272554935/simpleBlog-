package com.mtw.blog_vue.controller.admin;

import com.mtw.blog_vue.constant.WebConst;
import com.mtw.blog_vue.pojo.ArticleDomain;
import com.mtw.blog_vue.pojo.Article_Label;
import com.mtw.blog_vue.pojo.LabelDomain;
import com.mtw.blog_vue.service.Ariticle_LabelService;
import com.mtw.blog_vue.service.ArticleService;
import com.mtw.blog_vue.service.LabelService;
import com.mtw.blog_vue.utils.CacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleController {


    private static final Logger LOGGER = LogManager.getLogger(ArticleController.class);

    @Autowired
    ArticleService articleService;

    @Autowired
    LabelService labelService;

    @Autowired
    Ariticle_LabelService ariticle_labelService;


    /**
     * 发布文章
     * @param content
     * @param type
     * @param title
     * @param info
     * @param labels
     * @param titleImg
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @PostMapping("/admin/push/add")
    public int pushArticle(
            @RequestParam(value = "content") String content,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "info") String info,
            @RequestParam(value = "label") String[] labels,
            @RequestParam(value = "titleImg",required = false) String titleImg,
            HttpServletRequest request

    ){
        String screenName = (String)request.getSession().getAttribute("screenName");
        ArticleDomain articleDomain = new ArticleDomain();
        articleDomain.setContent(content);
        articleDomain.setType(type);
        articleDomain.setTitle(title);
        articleDomain.setInfo(info);
        articleDomain.setState(1);
        articleDomain.setAuthor(screenName);
        if (titleImg != null) {
            articleDomain.setTitlePic(titleImg);
        }
        try{
            this.addArticle(labels,articleDomain);
            HitCount();
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return 500;
        }
        return 200;
    }

    /**
     * 存为草稿
     * @param content
     * @param type
     * @param title
     * @param info
     * @param labels
     * @param titleImg
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @PostMapping("/admin/push/draft")
    public int addDraft(
            @RequestParam(value = "content") String content,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "info") String info,
            @RequestParam(value = "label") String[] labels,
            @RequestParam(value = "titleImg",required = false) String titleImg,
            HttpServletRequest request

    ){
        String screenName = (String)request.getSession().getAttribute("screenName");
        ArticleDomain articleDomain = new ArticleDomain();
        articleDomain.setContent(content);
        articleDomain.setType(type);
        articleDomain.setTitle(title);
        articleDomain.setInfo(info);
        articleDomain.setState(0);
        articleDomain.setAuthor(screenName);
        if (titleImg != null){
            articleDomain.setTitlePic(titleImg);
        }
        try{
            addArticle(labels,articleDomain);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return 500;
        }
        return 200;
    }

    /**
     * 草稿发布
     * @param aid
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @GetMapping("/admin/draft2push")
    public int pushDraft(
            @RequestParam(value = "aid") Integer aid
    ){
        ArticleDomain updateDraft = new ArticleDomain();
        updateDraft.setAid(aid);
        updateDraft.setState(1);
        try{
            articleService.updateArticle(updateDraft);
            HitCount();
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return 500;
        }
        return 200;
    }

    /**
     * 更新草稿
     * @param aid
     * @param content
     * @param type
     * @param title
     * @param info
     * @param titleImg
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @PostMapping("/admin/push/save")
    public int saveDraft(
            @RequestParam(value = "aid") Integer aid,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "info") String info,
            @RequestParam(value = "label") String[] labels,
            @RequestParam(value = "titleImg",required = false) String titleImg

    ){

        ArticleDomain articleDomain = new ArticleDomain();
        articleDomain.setAid(aid);
        articleDomain.setContent(content);
        articleDomain.setType(type);
        articleDomain.setTitle(title);
        articleDomain.setInfo(info);
        articleDomain.setState(0);
        if (titleImg != null){
            articleDomain.setTitlePic(titleImg);
        }
        try{
            articleService.updateArticle(articleDomain);
            //更新文章-标签中间表
            List<Article_Label> byAid = ariticle_labelService.getByAid(aid);
            if (byAid !=null){
                ariticle_labelService.deleteByAid(aid);
            }
            addLabel(labels,aid);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return 500;
        }
        return 200;
    }

    /**
     * 删除文章
     * @param aid
     * @return
     */
    @RequiresRoles(value={"v8","admin"},logical= Logical.OR)
    @GetMapping("/admin/deleteArt")
    public int deleteArticle(
            @RequestParam(name = "aid") int aid
    ){
        try {
            articleService.deleteArticleById(aid);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return 500;
        }
        return 200;
    }

    /**
     * 持久化文章
     * @param labels  标签
     * @param art     文章实体
     */
    public void addArticle(String[] labels,ArticleDomain art){

        try{
            int aid = articleService.addArticle(art);
            addLabel(labels,aid);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }

    /**
     *持久化中间表
     * @param labels
     * @param aid
     */
    public void addLabel(String[] labels,int aid){

        //存入标签
        ArrayList<Article_Label> A_LList = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            LabelDomain byName = labelService.getByName(labels[i]);
            Article_Label article_label = new Article_Label();
            article_label.setLid(byName.getLid());
            article_label.setAid(aid);
            A_LList.add(article_label);
        }
        try{
          ariticle_labelService.addByList(A_LList);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 更新当天发布的文章数
     */
    public void HitCount(){
        CacheManager manager = CacheManager.getInstance();
        Integer hits = manager.get(WebConst.articleKey);
        manager.put(WebConst.articleKey,hits+1);
    }
}
