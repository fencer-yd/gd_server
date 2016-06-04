package com.ngj.user.mapper;

import com.ngj.user.modle.Company;
import com.ngj.user.modle.Companydetail;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by guanxinquan on 16/3/4.
 */

public interface CompanyMapper {

    static final String SELECT_ALL = "select id,name,ctime,utime,maxUser,deadLine,status,domain from company ";

    @Select( SELECT_ALL+" where status = 1")
    List<Company> listCompany();

    @Insert("insert into company (name,domain,ctime,utime,maxUser,deadLine,status) " +
            "values(#{name},#{domain},#{ctime},#{utime},#{maxUser},#{deadLine},#{status})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insertCompany(Company company);

    @Update("update company set name = #{name} ,utime = #{utime} where id=#{id}")
    void updateCompany(Company company);

    @Select(SELECT_ALL+" where id=#{id}")
    Company selectById(Long id);

    @Select(SELECT_ALL + " where name = #{name}")
    Company selectByName(@Param("name")String name);

    @Select(SELECT_ALL + " where name = #{name}")
    List<Company> findByName(@Param("name")String name);

    @Select(SELECT_ALL +" where domain = #{domain}")
    Company selectCompanyByDomain(String domain);

    @Select("select company.id , company.name , `customer`.`address` , `customer`.`contact`,`customer`.`size`, `customer`.`describle`,`company`.`ctime` cTime, `company`.`utime` uTime from company , customer where company.id = #{id} and company.id = customer.tenant")
    List<Companydetail> findCompanyDeatilById(@Param("id")Long id);
    @Select("select company.id , company.name , `customer`.`address` , `customer`.`contact`,`customer`.`size`, `customer`.`describle`,`company`.`ctime` cTime, `company`.`utime` uTime from company , customer where company.id = customer.tenant")
    List<Companydetail> findCompanyDeatil();
}
