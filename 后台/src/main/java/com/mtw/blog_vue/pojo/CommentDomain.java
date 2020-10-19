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
@TableName(value = "blog_comment")
public class CommentDomain implements Serializable {

    @TableId(value = "cid",type = IdType.AUTO)
    private Integer cid;
    //用户id
    @TableField(value = "aid")
    private Integer aid;
    //评论者id
    @TableField(value = "authorId")
    private Integer authorId;
    //评论的内容
    @TableField(value = "content")
    private String content;
    //评论者昵称
    @TableField(value = "author")
    private String author;
    //
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    //
    @TableField(value = "updateTime",fill = FieldFill.UPDATE)
    private Date updateTime;

    private String formatTime;
}
