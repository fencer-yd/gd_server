package com.ngj.customer.mapper;

import com.ngj.customer.model.Customer;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/22.
 */
public interface CustomerMapper {
    static final String SELECT_ALL = "select tenant,name,address,contact,size,desc from customer";

    @Insert("insert into customer (tenant,name,address,contact,size,describle,cTime,uTime,status) values" +
            " (#{tenant},#{name},#{address},#{contact},#{size},#{describle},#{cTime},#{uTime},#{status})")
    Long insert(Customer customer);

    @Update("update customer set status = 0 where tenant = #{tenant}")
    void deleteById(Long tenant);

    @Update("update customer set name = #{name} ,address=#{address},contact=#{contact},size=#{size},desc=#{desc},uTime=#{uTime} where tenant = #{tenant}")
    void update(Customer Customer);

    @Select(SELECT_ALL +" where status = 1 and id>#{start} limit 0,#{limit}")
    List<Customer> selectAll(@Param("start") Long start,
                             @Param("limit") Integer limit);

    @Select("select count(*) from customer where status =1")
    Integer countCustomer();

}
