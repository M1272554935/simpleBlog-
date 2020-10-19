package com.mtw.blog_vue.mapper;

import com.mtw.blog_vue.pojo.dto.Art_LabDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 标签左外查询
 *
 */
@Mapper
public interface A_LJoinMapper {

    List<Art_LabDto> getList(Integer aid);

}
