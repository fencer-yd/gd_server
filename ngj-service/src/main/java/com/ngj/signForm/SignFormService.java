package com.ngj.signForm;

import com.ngj.signForm.model.SignForm;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
public interface SignFormService {
    Long insert(SignForm signForm);
    void update(SignForm signForm);
    void deleteByContractId(Long contractId);
    SignForm findByContract(Long contractId);
    List<SignForm> findByTenant(Long tenantId);
    List<SignForm> findAll();

}
