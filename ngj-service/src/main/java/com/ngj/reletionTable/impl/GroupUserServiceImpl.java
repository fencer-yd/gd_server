package com.ngj.reletionTable.impl;

import com.ngj.reletionTable.GroupUserService;
import com.ngj.reletionTable.mapper.GroupUserMapper;
import com.ngj.reletionTable.model.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/22.
 */
@Component("groupUserService")
public class GroupUserServiceImpl implements GroupUserService{
    @Autowired
    GroupUserMapper mapper;
    public void insert(UserGroup userGroup) {
        mapper.insert(userGroup);
    }

    public void deleteByUserId(Long userId) {
        mapper.deleteByUserId(userId);
    }

    public void deleteByGroupId(Long groupId) {
        mapper.deleteByGroupId(groupId);
    }

    public List<UserGroup> selectByUserId(Long userId) {
        return mapper.selectByUserId(userId);
    }

    public List<UserGroup> selectByGroupId(Long groupId) {
        return mapper.selectByGroupId(groupId);
    }
}
