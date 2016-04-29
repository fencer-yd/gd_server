package com.ngj.reletionTable.mapper;

import com.ngj.reletionTable.model.GroupMenu;
import com.ngj.reletionTable.model.UserGroup;
import com.ngj.user.modle.Group;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/21.
 */
public interface GroupUserMapper {
    static final String SELECT_ALL = "select group_id from user_group";
    @Insert("insert into user_group (user_id,group_id) values (#{user_id},#{group_id})")
    void insert(UserGroup userGroup);
    @Delete("delete from user_group where user_id = #{user_id}")
    void deleteByUserId(@Param("user_id") Long user_id);
    @Select( SELECT_ALL+" where user_id=#{user_id}")
    void deleteByGroupId(@Param("group_id") Long group_id);
    @Select( SELECT_ALL+" where user_id=#{user_id}")
    List<UserGroup> selectByUserId(@Param("user_id") Long user_id);
    @Select( SELECT_ALL+" where group_id=#{group_id}")
    List<UserGroup> selectByGroupId(@Param("group_id") Long group_id);
}
