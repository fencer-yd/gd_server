package com.ngj.contract.mapper;
import com.ngj.contract.model.Contract;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import java.util.List;
/**
 * Created by pangyueqiang on 16/4/24.
 */
public interface ContractMapper {
    static final String SELECT_ALL = "select * from contract";
    @Insert("insert into contract (name,part_a,part_b,content,effectiveTime,expirationTime,keyWords,cTime,uTime,situation,status) values(#{name},#{part_a},#{part_b},#{content},#{effectiveTime},#{expirationTime},#{keyWords},#{cTime},#{uTime},#{situation},#{status})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insert(Contract contract);

    @Update("update contract set name = #{name},part_a=#{part_a},part_b=#{part_b},content=#{content},effectiveTime=#{effectiveTime},expirationTime=#{expirationTime},keyWords=#{keyWords},uTime=#{uTime},situation=#{situation} where id = #{id}")
    void update(Contract contract);
    @Select(SELECT_ALL+"where status = 1")
    List<Contract> selectAll();
    @Select(SELECT_ALL+"where part_a = #{tenant} or part_b =#{tenant} and status = 1")
    List<Contract> findByTenantId(
            @Param("tenant") Long tenant
    );
    @Update("update contract set status = 0 where id = #{id}")
    void deleteById(@Param("id")Long id);

}
