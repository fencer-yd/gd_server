package com.ngj.signForm.impl;

import com.ngj.signForm.SignFormService;
import com.ngj.signForm.mapper.SignFormMapper;
import com.ngj.signForm.model.SignForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Component("signFormService")
public class SignFormServiceImpl implements SignFormService{
    @Autowired
    private SignFormMapper mapper;
    public Long insert(SignForm signForm) {
        return mapper.insert(signForm);
    }

    public void update(SignForm signForm) {
        mapper.update(signForm);
    }

    public void deleteByContractId(Long contractId) {
        mapper.deleteByContractId(contractId);
    }

    public SignForm findByContract(Long contractId) {
        return mapper.findByContract(contractId);
    }

    public List<SignForm> findByTenant(Long tenantId) {
        return mapper.findByTenant(tenantId);
    }

    public List<SignForm> findAll() {
        return mapper.findAll();
    }
}
