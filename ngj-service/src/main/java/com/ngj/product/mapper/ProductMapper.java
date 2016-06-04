package com.ngj.product.mapper;

import com.ngj.product.model.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
public interface ProductMapper {
    @Insert("insert into product (tenant,name,`desc`,leader,cTime,uTime,status) values(#{tenant},#{name},#{desc},#{leader},#{cTime},#{uTime},#{status})")
    Long insert(Product product);
    @Update("update product set name=#{name},`desc`=#{desc},leader=#{leader},uTime=#{uTime} where id = #{id}")
    void update(Product product);
    @Select("select * from product where status = 1 and tenant = #{tenant}")
    List<Product> findByTenant(Long tenant);
    @Update("update product set status = 0 where id = #{id}")
    void deleteById(Long id);

}
