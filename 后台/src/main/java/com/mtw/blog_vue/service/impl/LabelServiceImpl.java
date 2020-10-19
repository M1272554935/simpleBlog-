package com.mtw.blog_vue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtw.blog_vue.mapper.LabelMapper;
import com.mtw.blog_vue.pojo.LabelDomain;
import com.mtw.blog_vue.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    LabelMapper labelMapper;

    @Override
    public  LabelDomain getByName(String name){
        QueryWrapper<LabelDomain> wrapper = new QueryWrapper<>();
        wrapper.eq("lname",name);
        LabelDomain labelDomain = labelMapper.selectOne(wrapper);

        return labelDomain;
    }

    @Override
    public void AddLabel(LabelDomain label) {
        LabelDomain byName = getByName(label.getLname());
        if (byName == null){
            labelMapper.insert(label);
        }

    }

    @Override
    public void deletLabelById(Integer lid) {
        LabelDomain labelById = getLabelById(lid);
        if ( labelById != null){
            labelMapper.deleteById(lid);
        }

    }

    @Override
    public LabelDomain getLabelById(Integer lid) {
        return labelMapper.selectById(lid);
    }

    @Override
    public List<LabelDomain> getAllLabel() {
        QueryWrapper<LabelDomain> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("lid");
        List<LabelDomain> labelDomains = labelMapper.selectList(wrapper);

        return labelDomains;
    }
}
