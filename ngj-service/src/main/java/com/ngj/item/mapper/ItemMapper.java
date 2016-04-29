package com.ngj.item.mapper;

import com.ngj.item.model.Item;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
public interface ItemMapper {
    static final String SELECT_ALL = "select id,name,tenant,content,cTime,uTime,status from item ";

    @Insert("insert into item (name,tenant,content,cTime,uTime,status) values (#{name},#{tenant},#{content},#{cTime},#{uTime},#{status})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insert(Item item);

    @Update("update item set name=#{name},tenant=#{tenant},content=#{content},uTime=#{uTime},status=#{status} where id = #{id}")
    void update(Item item);

    @Delete("delete from item where id = #{id}")
    void deleteById(Long id);
    @Select(SELECT_ALL + "where tenant=#{tenant} and status = 1")
    List<Item> findByTenant(@Param("tenant") Long tenant);
}
