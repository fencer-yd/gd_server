package com.ngj.item;

import com.ngj.item.model.Item;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
public interface ItemService {
    Long insert(Item item);
    void update(Item item);
    void deleteById(Long id);
    List<Item> findByTenant(Long tenant);
}
