package com.ngj.user.mapper;

import com.ngj.user.modle.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by guanxinquan on 16/2/29.
 */
public interface UserMapper {

    static final String SELECT_ALL = "select id, username,password,tenant,role,ctime,utime,mobile,name from user ";

    @Insert("insert into user (username,password,tenant,role,ctime,utime,mobile,name,refresh) values (#{username},#{password},#{tenant},#{role},#{ctime},#{utime},#{mobile},#{name},#{refresh})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insert(User user);

    @Select(SELECT_ALL+"where id = #{userId}")
    User loadById(Long userId);

    @Select(SELECT_ALL+"where username=#{username} and tenant=#{tenant}")
    User loadByNameAndTenantId(@Param("username")String userName,@Param("tenant")Long tenant);
    @Select(SELECT_ALL+"where username=#{username}")

    List<User> selectUserByUserName(@Param("username")String userName);

    @Update("update user set name=#{name},username=#{username},password=#{password},role=#{role},utime=#{utime},mobile=#{mobile},name=#{name},refresh=#{refresh} where id=#{id}")
    void update(User user);

    @Select(SELECT_ALL +" where tenant=#{tenant} and id>#{start} limit 0,#{limit}")
    List<User> selectUserByTenant(@Param("tenant") Long tenant,
                                  @Param("start") Long start,
                                  @Param("limit") Integer limit);


    @Select("select count(*) from user where tenant=#{tenant}")
    Integer countUser(@Param("tenant") Long tenant);

}
