package com.ngj.user.mapper;

import com.ngj.user.modle.Group;
import com.ngj.user.modle.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/21.
 */
public interface GroupMapper {
    static final String SELECT_ALL = "select id, tenant,name,cTime,uTime from `group` ";
    @Insert("insert into `group` (tenant,name,cTime,uTime,status) values (#{tenant},#{name},#{cTime},#{uTime},#{status}) ")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insert(Group group);

    @Update("update `group` set status = 0 where id = #{id}")
    void deleteById(Long id);

    @Update("update `group` set name = #{name} , uTime = #{uTime} where id = #{id}")
    void update(Group group);

    @Select(SELECT_ALL +" where tenant=#{tenant} and status = 1 ")
    List<Group> selectByTenantId(@Param("tenant") Long tenant);

    @Select(SELECT_ALL +" where id=#{id} and status = 1 ")
    Group selectById(@Param("id") Long id);
}
