package com.ngj.user;

import com.ngj.customer.model.Customer;
import com.ngj.user.modle.Company;
import com.ngj.user.modle.Companydetail;

import java.util.List;

/**
 * Created by guanxinquan on 16/3/4.
 */
public interface CompanyService {

    Long addCompany(Company company);

    void changeCompany(Company company);
    void changeCustomer(Customer customer);
    List<Company> listCompany();
    Company findCompany(Long id);
    Company findCompanyByName(String name);
    List<Company> validatorByName(String name);
    Company findCompanyByDomin(String domin);
    List<Companydetail> findCompanyDetailByid(Long id);
    List<Companydetail> findCompanyDetail();





}
