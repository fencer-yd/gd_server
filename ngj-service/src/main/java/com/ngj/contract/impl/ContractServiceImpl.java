package com.ngj.contract.impl;

import com.alibaba.druid.util.StringUtils;
import com.ngj.contract.ContractService;
import com.ngj.contract.mapper.ContractMapper;
import com.ngj.contract.model.Contract;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/24.
 */
@Component("contractService")
public class ContractServiceImpl implements ContractService{
    @Autowired
    private ContractMapper mapper;
    public Long insert(Contract contract) {
        if(!StringUtils.isEmpty(contract.getContent()))
        {
            contract.setContent(StringEscapeUtils.escapeHtml4(contract.getContent()));
        }
        return mapper.insert(contract);
    }

    public void update(Contract contract) {
        if(!StringUtils.isEmpty(contract.getContent()))
        {
            contract.setContent(StringEscapeUtils.escapeHtml4(contract.getContent()));
        }
        mapper.update(contract);
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public List<Contract> selectByTenant(Long tenant) {
        List<Contract> contracts = mapper.findByTenantId(tenant);
        if(!CollectionUtils.isEmpty(contracts))
        {
            for(Contract contract : contracts)
            {
                contract.setContent(StringEscapeUtils.escapeHtml4(contract.getContent()));

            }
        }
        return contracts;
    }

}
