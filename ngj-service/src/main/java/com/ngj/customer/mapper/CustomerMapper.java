package com.ngj.customer.mapper;

import com.ngj.customer.model.Customer;
import com.ngj.user.modle.Companydetail;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/22.
 */
public interface CustomerMapper {
    static final String SELECT_ALL = "select tenant,name,address,contact,size,describle from customer ";

    @Insert("insert into customer (tenant,name,address,contact,size,describle,cTime,uTime,status) values" +
            " (#{tenant},#{name},#{address},#{contact},#{size},#{describle},#{cTime},#{uTime},#{status})")
    Long insert(Customer customer);

    @Update("update customer set status = 0 where tenant = #{tenant}")
    void deleteById(Long tenant);

    @Update("update customer set name = #{name} ,address=#{address},contact=#{contact},size=#{size},describle=#{describle},uTime=#{uTime} where tenant = #{tenant}")
    void update(Customer Customer);

    @Select(SELECT_ALL +" where status = 1 and tenant <> #{tenantId}")
    List<Customer> selectAll(Long tenantId);
    @Select(SELECT_ALL +" where status = 1 and tenant = #{tenantId}")
    Customer selectById(Long tenantId);
    @Select("select count(*) from customer where status =1")
    Integer countCustomer();


}
