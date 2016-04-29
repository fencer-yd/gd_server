package com.ngj.templet.mapper;

import com.ngj.templet.model.Templet;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/24.
 */
public interface TempletMapper {
    static final String SELECT_ALL = "select * from templet";

    @Insert("insert into templet(name,content,userId,type,cTime,uTime,status) values(#{name},#{content},#{userId},#{type},#{cTime},#{uTime},#{status})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insert(Templet templet);

    @Select(SELECT_ALL+"where status = 1 and  userId = #{userId}")
    List<Templet> selectByUserId(@Param("userId")Long userId);

    @Select(SELECT_ALL+"where status = 1 and  type = #{type}")
    List<Templet> selectByType(@Param("type")Integer type);

    @Update("update templet set name = #{name},content=#{content},userId=#{userId},type=#{type},uTime=#{uTime} where id = #{id}")
    void update(Templet templet);
    @Delete("delete from templet where id = #{id} ")
    void deleteById(@Param("id")Long id);

}
