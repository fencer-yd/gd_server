package com.ngj.user.mapper;

import com.ngj.user.modle.Company;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by guanxinquan on 16/3/4.
 */

public interface CompanyMapper {

    static final String SELECT_ALL = "select id,name,ctime,utime,maxUser,deadLine,status,domain from company ";

    @Select( SELECT_ALL+" where id > #{start} limit 0,#{limit}")
    List<Company> listCompany(@Param("start")Long start,@Param("limit")Integer limit);

    @Insert("insert into company (name,domain,ctime,utime,maxUser,deadLine,status) " +
            "values(#{name},#{domain},#{ctime},#{utime},#{maxUser},#{deadLine},#{status})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insertCompany(Company company);

    @Update("update company (name,utime,maxUser,deadLine,status)" +
            " valuse(#{name},#{utime},#{maxUser},#{deadLine},#{status}) where id=#{id}")
    void updateCompany(Company company);

    @Select(SELECT_ALL+" where id=#{id}")
    Company selectById(Long id);

    @Select(SELECT_ALL + " where name = #{name}")
    Company selectByName(@Param("name")String name);

    @Select(SELECT_ALL +" where domain = #{domain}")
    Company selectCompanyByDomain(String domain);

}
