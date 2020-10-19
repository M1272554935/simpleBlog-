package com.mtw.blog_vue.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName(value = "article_label")
public class Article_Label {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "aid")
    private Integer aid;

    @TableField(value = "lid")
    private Integer lid;
    //
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    //
    @TableField(value = "updateTime",fill = FieldFill.UPDATE)
    private Date updateTime;
}
