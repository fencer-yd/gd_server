package com.ngj.templet;

import com.ngj.templet.model.Templet;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/24.
 */
public interface TempletService {
    Long insert(Templet templet);
    List<Templet> selectByUser(Long userId);
    List<Templet> selectByType(Integer type);
    void update(Templet templet);
    void deleteById(Long id);
}
