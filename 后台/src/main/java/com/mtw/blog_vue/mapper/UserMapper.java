package com.mtw.blog_vue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtw.blog_vue.pojo.UserDomain;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserDomain> {
}
