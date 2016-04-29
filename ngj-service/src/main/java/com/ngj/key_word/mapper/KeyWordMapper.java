package com.ngj.key_word.mapper;

import com.ngj.item.model.Item;
import com.ngj.key_word.model.KeyWord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
public interface KeyWordMapper {
    static final String SELECT_ALL = "select id,name,tenant,cTime,uTime,status from key_word ";

    @Insert("insert into key_word (name,tenant,cTime,uTime,status) values (#{name},#{tenant},#{cTime},#{uTime},#{status})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insert(KeyWord keyWord);

    @Update("update key_word set name=#{name},tenant=#{tenant},uTime=#{uTime},status=#{status} where id = #{id}")
    void update(KeyWord keyWord);

    @Delete("delete from key_word where id = #{id}")
    void deleteById(Long id);
    @Select(SELECT_ALL + "where tenant=#{tenant} and status = 1")
    List<Item> findByTenant(@Param("tenant") Long tenant);
}
