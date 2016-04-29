package com.ngj.key_word;

import com.ngj.item.model.Item;
import com.ngj.key_word.model.KeyWord;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
public interface KeyWordService {
    Long insert(KeyWord keyWord);
    void update(KeyWord keyWord);
    void deleteById(Long id);
    List<Item> findByTenant(Long tenant);

}
