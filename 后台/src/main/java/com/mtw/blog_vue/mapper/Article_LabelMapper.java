package com.mtw.blog_vue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtw.blog_vue.pojo.Article_Label;
import org.springframework.stereotype.Repository;

/**
 * 文章-标签中间表
 */
@Repository
public interface Article_LabelMapper extends BaseMapper<Article_Label> {
}
