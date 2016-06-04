package com.ngj.customer;

import com.ngj.customer.model.Customer;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/22.
 */
public interface CustomerService {
    Long insert(Customer customer);
    void update(Customer customer);
    void deleteByTenantId(Long tenant);
    List<Customer> selectAll(Long tenantId);
}
