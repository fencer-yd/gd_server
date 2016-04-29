package com.ngj.item.impl;

import com.alibaba.druid.util.StringUtils;
import com.ngj.item.ItemService;
import com.ngj.item.mapper.ItemMapper;
import com.ngj.item.model.Item;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Component("itemService")
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper mapper;
    public Long insert(Item item) {
        if (!StringUtils.isEmpty(item.getContent()))
        {
            item.setContent(StringEscapeUtils.escapeHtml4(item.getContent()));
        }
        return  mapper.insert(item);
    }

    public void update(Item item) {
        if (!StringUtils.isEmpty(item.getContent()))
        {
            item.setContent(StringEscapeUtils.escapeHtml4(item.getContent()));
        }
        mapper.update(item);
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public List<Item> findByTenant(Long tenant) {
        List<Item> items = mapper.findByTenant(tenant);
        if(!CollectionUtils.isEmpty(items))
        {
            for(Item item :items) {
                item.setContent(StringEscapeUtils.unescapeHtml4(item.getContent()));
            }

        }
        return items;
    }
}
