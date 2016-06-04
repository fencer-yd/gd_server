package com.ngj.contract.mapper;
import com.ngj.contract.model.Contract;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import java.util.List;
/**
 * Created by pangyueqiang on 16/4/24.
 */
public interface ContractMapper {
    static final String SELECT_ALL = "select * from contract ";
    @Insert("insert into contract (name,party_a,party_b,content,effectiveTime,expirationTime,templetId,keyWords,cTime,uTime,situate,status) values(#{name},#{party_a},#{party_b},#{content},#{effectiveTime},#{expirationTime},#{templetId},#{keyWords},#{cTime},#{uTime},#{situate},#{status})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insert(Contract contract);

    @Update("update contract set name = #{name},party_a=#{party_a},party_b=#{party_b},content=#{content},effectiveTime=#{effectiveTime},expirationTime=#{expirationTime},keyWords=#{keyWords},templetId=#{templetId},uTime=#{uTime},situate=#{situate} where id = #{id}")
    void update(Contract contract);
    @Select(SELECT_ALL+"where status = 1")
    List<Contract> selectAll();
    @Select(SELECT_ALL+"where party_a = #{tenant}  and status  =1 or party_b =#{tenant} and status = 1")
    List<Contract> findByTenantId(
            @Param("tenant") Long tenant
    );
    @Update("update contract set status = 0 where id = #{id}")
    void deleteById(@Param("id")Long id);

}
