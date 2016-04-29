package com.ngj.reletionTable;

import com.ngj.reletionTable.model.UserGroup;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/21.
 */
public interface GroupUserService {
    void insert(UserGroup userGroup);
    void deleteByUserId(Long userId);
    void deleteByGroupId(Long groupId);
    List<UserGroup> selectByUserId(Long userId);
    List<UserGroup> selectByGroupId(Long groupId);
}
