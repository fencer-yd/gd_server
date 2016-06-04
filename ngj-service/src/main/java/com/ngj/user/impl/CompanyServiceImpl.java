package com.ngj.user.impl;

import com.ngj.customer.mapper.CustomerMapper;
import com.ngj.customer.model.Customer;
import com.ngj.user.CompanyService;
import com.ngj.user.mapper.CompanyMapper;
import com.ngj.user.modle.Company;
import com.ngj.user.modle.Companydetail;
import com.ngj.utils.PropertiesCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by guanxinquan on 16/3/4.
 */
@Component("companyService")
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private CompanyMapper mapper ;
    @Autowired
    private CustomerMapper customerMapper ;

    public Long addCompany(Company company) {

        Company old = mapper.selectByName(company.getName());
        if(old != null){//如果公司名称已经存在,那么直接返回-1
            return -1l;
        }
        return mapper.insertCompany(company);
    }

    public void changeCompany(Company company) {
        Long cruTime = System.currentTimeMillis();
        Company old = mapper.selectById(company.getId());
        old.setName(company.getName());
        old.setUTime(cruTime);
        old.setUtime(cruTime);
        mapper.updateCompany(company);

    }

    public void changeCustomer(Customer customer) {
        Long cruTime = System.currentTimeMillis();
        Customer oldCustomer = customerMapper.selectById(customer.getTenant());
        oldCustomer.setUTime(cruTime);
        oldCustomer.setName(customer.getName());
        oldCustomer.setAddress(customer.getAddress());
        oldCustomer.setContact(customer.getContact());
        oldCustomer.setDescrible(customer.getDescrible());
        oldCustomer.setSize(customer.getSize());
        customerMapper.update(oldCustomer);
    }

    public List<Company> listCompany() {
        return mapper.listCompany();
    }

    public Company findCompany(Long id) {
        return mapper.selectById(id);
    }

    public Company findCompanyByName(String name) {
        return mapper.selectByName(name);
    }

    public List<Company> validatorByName(String name) {
        return mapper.findByName(name);
    }

    public Company findCompanyByDomin(String domin) {
        return mapper.selectCompanyByDomain(domin);
    }

    public List<Companydetail> findCompanyDetailByid(Long id) {
        return mapper.findCompanyDeatilById(id);
    }

    public List<Companydetail> findCompanyDetail() {
        return mapper.findCompanyDeatil();
    }
}
