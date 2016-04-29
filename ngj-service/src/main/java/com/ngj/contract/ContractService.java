package com.ngj.contract;

import com.ngj.contract.model.Contract;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/22.
 */
public interface ContractService {
    Long insert(Contract contract);
    void update(Contract contract);
    void deleteById(Long id);
    List<Contract> selectByTenant(Long tenant);
}
