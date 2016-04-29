package com.ngj.user;

import com.ngj.user.modle.Company;

import java.util.List;

/**
 * Created by guanxinquan on 16/3/4.
 */
public interface CompanyService {

    Long addCompany(Company company);

    void changeCompany(Company company);

    List<Company> listCompany(Long start,Integer limit);

    Company findCompany(Long id);
    Company findCompanyByName(String name);
    Company findCompanyByDomin(String domin);


}
