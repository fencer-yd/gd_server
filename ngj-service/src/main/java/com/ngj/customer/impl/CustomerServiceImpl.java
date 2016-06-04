package com.ngj.customer.impl;

import com.ngj.customer.CustomerService;
import com.ngj.customer.mapper.CustomerMapper;
import com.ngj.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/22.
 */
@Component("customerService")
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerMapper mapper;

    public Long insert(Customer customer) {
        return mapper.insert(customer);

    }

    public void update(Customer customer) {
        mapper.update(customer);
    }

    public void deleteByTenantId(Long tenant) {
        mapper.deleteById(tenant);
    }

    public List<Customer> selectAll(Long tenantId) {
        return mapper.selectAll(tenantId);
    }

}
