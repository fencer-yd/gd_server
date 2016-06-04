package com.ngj.reletionTable.mapper;

import com.ngj.reletionTable.model.GroupMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/21.
 */
public interface GroupMenuMapper {
    static final String SELECT_ALL = "select menu_id from group_menu";
    @Insert("insert into group_menu (group_id,tenant,menu_id) values (#{group_id},#{tenant},#{menu_id})")
    void insert(GroupMenu groupMenu);
    @Delete("delete from group_menu where group_id = #{group_id}")
    void delete(@Param("group_id") Long group_id);
    @Select( SELECT_ALL+" where group_id=#{group_id}")
    List<GroupMenu> selectByGroupId(@Param("group_id") Long group_id);
}
