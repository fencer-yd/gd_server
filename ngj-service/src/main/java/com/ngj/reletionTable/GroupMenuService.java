package com.ngj.reletionTable;

import com.ngj.reletionTable.model.GroupMenu;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/21.
 */
public interface GroupMenuService {
    void insert(GroupMenu groupMenu);
    void deleteByGroupId(Long group_id);
    List<GroupMenu> selectByGroupId(Long group_id);
}
