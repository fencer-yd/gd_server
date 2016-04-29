package com.ngj.reletionTable.impl;

import com.ngj.reletionTable.GroupMenuService;
import com.ngj.reletionTable.mapper.GroupMenuMapper;
import com.ngj.reletionTable.model.GroupMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/22.
 */
@Component("groupMenuService")
public class GroupMenuServiceImpl implements GroupMenuService{
    @Autowired
    private GroupMenuMapper mapper;

    public void insert(GroupMenu groupMenu) {
        mapper.insert(groupMenu);
    }

    public void deleteByGroupId(Long group_id) {
        mapper.delete(group_id);
    }

    public List<GroupMenu> selectByGroupId(Long group_id) {
        return mapper.selectByGroupId(group_id);
    }
}
