package com.mtw.blog_vue.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName(value = "blog_article")
public class ArticleDomain implements Serializable {

    //文章id
    @TableId(value = "aid",type = IdType.AUTO)
    private Integer aid;
    //文章标题
    @TableField(value = "title")
    private String title;
    //标题图片
    @TableField(value = "titlePic")
    private String titlePic;
    //简介
    @TableField(value = "info")
    private String info;
    //文章内容
    @TableField(value = "content")
    private String content;
    //作者名称
    @TableField(value = "author")
    private String author;
    //作者id
    @TableField(value = "authorId")
    private Integer authorId;
    //文章状态，0为草稿，1为发布
    @TableField(value = "state")
    private Integer state;
    //类型
    @TableField(value = "type")
    private String type;
    //点击量
    @TableField(value = "hits")
    private Integer hits;
    //评论数
    @TableField(value = "commentsNum")
    private Integer commentsNum;
    //
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    //
    @TableField(value = "updateTime",fill = FieldFill.UPDATE)
    private Date updateTime;


}
