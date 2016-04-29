package com.ngj.signForm.mapper;

import com.ngj.signForm.model.SignForm;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
public interface SignFormMapper{
    static final String SELLECT_ALL = "select * from sign_form";
    @Insert("insert into sign_form (contractId,part_a,part_b,part_a_opinion,part_b_opinion,cTime,uTime,situation,status) values(#{contractId},#{part_a},#{part_b},#{part_a_opinion},#{part_b_opinion},#{cTime},#{uTime},#{situation},#{status})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insert(SignForm signForm);
    @Update("update sign_form set part_a_opinion=#{part_a_opinion},part_b_opinion=#{part_b_opinion},uTime=#{uTime},situation=#{situation} where id = #{id}")
    void update(SignForm signForm);

    @Delete("delete from sign_form where id = #{id}")
    void deleteById(Long id);

    @Delete("delete from sign_form where contractId = #{contractId}")
    void deleteByContractId(Long contractId);

    @Select(SELLECT_ALL+"where contractId = #{contractId}")
    SignForm findByContract(Long contractId);

    @Select(SELLECT_ALL+"where part_a=#{tenantId} or part_b = #{tenantId}")
    List<SignForm> findByTenant(Long tenantId);

    @Select(SELLECT_ALL+"where status = 1")
    List<SignForm> findAll();




}
