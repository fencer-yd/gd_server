package com.ngj.user;

import com.ngj.user.modle.Group;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/22.
 */
public interface GroupService {
    Long insert(Group group);
    void update(Group group);
    void deleteById(Long id);
    List<Group> selectByTenant(Long tenant);
    Group selectById(Long id);

}
