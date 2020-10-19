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
@TableName(value = "blog_user")
public class UserDomain implements Serializable {

    //用户id
    @TableId(value = "uid",type = IdType.AUTO)
    private Integer uid;
    //用户名
    @TableField(value = "username")
    private String username;
    //用户密码
    @TableField(value = "password")
    private String password;
    //用户邮箱
    @TableField(value = "email")
    private String email;
    //用户角色
    @TableField(value = "role")
    private String role;
    //用户昵称
    @TableField(value = "screenName")
    private String screenName;
    //
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    //
    @TableField(value = "updateTime",fill = FieldFill.UPDATE)
    private Date updateTime;

}
