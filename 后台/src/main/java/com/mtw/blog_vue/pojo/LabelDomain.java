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
@TableName(value = "blog_label")
public class LabelDomain implements Serializable {

    @TableId(value = "lid",type = IdType.AUTO)
    private Integer lid;
    @TableField(value = "lname")
    private String lname;
    //
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    //
    @TableField(value = "updateTime",fill = FieldFill.UPDATE)
    private Date updateTime;
}
