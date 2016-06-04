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
    @Insert("insert into sign_form (contractId,party_a,party_b,party_a_opinion,party_b_opinion,cTime,uTime,status) values(#{contractId},#{party_a},#{party_b},#{party_a_opinion},#{party_b_opinion},#{cTime},#{uTime},#{status})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    Long insert(SignForm signForm);
    @Update("update sign_form set party_a_opinion=#{party_a_opinion},party_b_opinion=#{party_b_opinion},uTime=#{uTime} where id = #{id}")
    void update(SignForm signForm);

    @Delete("delete from sign_form where id = #{id}")
    void deleteById(Long id);

    @Delete("delete from sign_form where contractId = #{contractId}")
    void deleteByContractId(Long contractId);

    @Select(SELLECT_ALL+" where contractId = #{contractId}")
    SignForm findByContract(Long contractId);

    @Select(SELLECT_ALL+" where party_a=#{tenantId} and status = 1 or party_b = #{tenantId} and status = 1")
    List<SignForm> findByTenant(Long tenantId);

    @Select(SELLECT_ALL+" where status = 1")
    List<SignForm> findAll();




}
