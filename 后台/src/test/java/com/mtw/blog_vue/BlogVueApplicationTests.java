package com.mtw.blog_vue;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtw.blog_vue.constant.WebConst;
import com.mtw.blog_vue.controller.HomeController;
import com.mtw.blog_vue.mapper.*;
import com.mtw.blog_vue.pojo.ArticleDomain;
import com.mtw.blog_vue.pojo.Article_Label;
import com.mtw.blog_vue.pojo.dto.Art_LabDto;
import com.mtw.blog_vue.service.Ariticle_LabelService;
import com.mtw.blog_vue.service.ArticleService;
import com.mtw.blog_vue.service.LabelService;
import com.mtw.blog_vue.service.PictureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogVueApplicationTests {

//    @Autowired
//    ArticleMapper articleMapper;
//    @Autowired
//    CommentMapper commentMapper;
//    @Autowired
//    LabelMapper labelMapper;
//    @Autowired
//    PictureMapper pictureMapper;
//    @Autowired
//    UserMapper userMapper;
//
    @Autowired
    A_LJoinMapper aLJoinMapper;
    @Autowired
    Article_LabelMapper article_labelMapper;

    @Autowired
    ArticleService articleService;
    @Autowired
    LabelService labelService;
    @Autowired
    PictureService pictureService;
    @Autowired
    Ariticle_LabelService a_llService;



    @Test
    void contextLoads() {

        Integer hitsCount = articleService.getHitsCount();
        System.out.println(hitsCount);


//        ArticleDomain articleDomain = new ArticleDomain();
//        articleDomain.setTitle("test1");
//        articleDomain.setInfo("test1");
//
//
//        int i = articleService.addArticle(articleDomain);
//        System.out.println(i);


//        Article_Label article_label = new Article_Label();
//        article_label.setAid(2);
//        article_label.setLid(1);
//
//        List<Art_LabDto> list = aLJoinMapper.getList(1);
//        System.out.println(list);


//        UserDomain userDomain = new UserDomain();
//        userDomain.setEmail("566");
//        userDomain.setPassword("123");
//        userDomain.setScreenName("wo");
//        userDomain.setUsername("shi");
//
//        LabelDomain labelDomain = new LabelDomain();
//        labelDomain.setLname("java");

//        CommentDomain commentDomain = new CommentDomain();
//        commentDomain.setAid(1);
//        commentDomain.setAuthor("2");
//        commentDomain.setContent("bbbbbbbbbbbbbbbbbbbbbbbbb");
//        commentDomain.setAuthorId(1);

//        PictureDomain pictureDomain = new PictureDomain();
//        pictureDomain.setAddress("5151515");
//        pictureDomain.setPname("sjhj");
//        pictureDomain.setUid(1);
//
//
//        commentMapper.insert(commentDomain);
//        CommentDomain commentDomain1 = commentMapper.selectById(2);
//        System.out.println(commentDomain1);
//        System.out.println("---------------------------------------------------------------------------");
//
//        pictureMapper.insert(pictureDomain);
//        PictureDomain pictureDomain1 = pictureMapper.selectById(1);
//        System.out.println(pictureDomain1);

    }


}
