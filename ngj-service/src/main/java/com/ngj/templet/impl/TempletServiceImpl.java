package com.ngj.templet.impl;

import com.alibaba.druid.util.StringUtils;
import com.ngj.templet.TempletService;
import com.ngj.templet.mapper.TempletMapper;
import com.ngj.templet.model.Templet;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/24.
 */
@Component("templetService")
public class TempletServiceImpl implements TempletService{
    @Autowired
    private TempletMapper mapper;

    public Long insert(Templet templet) {
        if (!StringUtils.isEmpty(templet.getContent()))
        {
            templet.setContent(StringEscapeUtils.escapeHtml4(templet.getContent()));
        }
       return  mapper.insert(templet);
    }

    public List<Templet> selectByUser(Long userId) {
        List<Templet> templets = mapper.selectByUserId(userId);
        if(!CollectionUtils.isEmpty(templets))
        {
            for(Templet templet:templets)
            {
                templet.setContent(StringEscapeUtils.unescapeHtml4(templet.getContent()));

            }
        }
        return templets;
    }

    public List<Templet> selectByType(Integer type) {
        List<Templet> templets = mapper.selectByType(type);
        if(!CollectionUtils.isEmpty(templets))
        {
            for(Templet templet:templets)
            {
                templet.setContent(StringEscapeUtils.unescapeHtml4(templet.getContent()));

            }
        }
        return templets;
    }
    public void update(Templet templet) {
        if (!StringUtils.isEmpty(templet.getContent()))
        {
            templet.setContent(StringEscapeUtils.escapeHtml4(templet.getContent()));
        }
       mapper.update(templet);
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }
}
