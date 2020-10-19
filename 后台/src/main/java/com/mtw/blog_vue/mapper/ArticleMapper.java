package com.mtw.blog_vue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtw.blog_vue.pojo.ArticleDomain;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper extends BaseMapper<ArticleDomain> {

}
