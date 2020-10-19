package com.mtw.blog_vue.service;

import com.mtw.blog_vue.pojo.LabelDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LabelService {

    void AddLabel(LabelDomain label);

    void deletLabelById(Integer lid);

    LabelDomain getLabelById(Integer lid);

    List<LabelDomain> getAllLabel();

    LabelDomain getByName(String name);
}
