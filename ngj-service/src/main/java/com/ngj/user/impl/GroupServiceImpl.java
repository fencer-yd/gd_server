package com.ngj.user.impl;

import com.ngj.user.GroupService;
import com.ngj.user.mapper.GroupMapper;
import com.ngj.user.modle.Group;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/22.
 */
public class GroupServiceImpl implements GroupService{
    @Autowired
    private GroupMapper mapper ;
    public Long insert(Group group) {
        return mapper.insert(group);
    }

    public void update(Group group) {
        mapper.update(group);
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public List<Group> selectByTenant(Long tenant) {
        return mapper.selectByTenantId(tenant);
    }
}
