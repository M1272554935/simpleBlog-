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
@TableName(value = "blog_picture")
public class PictureDomain implements Serializable {


    @TableId(value = "pid",type = IdType.AUTO)
    private Integer pid;
    //用户id
    @TableField(value = "uid")
    private Integer uid;
    //文章
    @TableField(value = "aid")
    private Integer aid;
    //文件名
    @TableField(value = "pname")
    private String pname;
    //地址
    @TableField(value = "address")
    private String address;
    //
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    //
    @TableField(value = "updateTime",fill = FieldFill.UPDATE)
    private Date updateTime;
}
