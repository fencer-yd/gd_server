package com.ngj.key_word.impl;

import com.ngj.item.model.Item;
import com.ngj.key_word.KeyWordService;
import com.ngj.key_word.mapper.KeyWordMapper;
import com.ngj.key_word.model.KeyWord;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
public class KeyWordServiceImpl implements KeyWordService{
    @Autowired
    private KeyWordMapper mapper;

    public Long insert(KeyWord keyWord) {
        return mapper.insert(keyWord);
    }

    public void update(KeyWord keyWord) {
        mapper.update(keyWord);
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public List<Item> findByTenant(Long tenant) {
        return mapper.findByTenant(tenant);
    }
}
