package com.ngj.user.impl;

import com.ngj.user.CompanyService;
import com.ngj.user.mapper.CompanyMapper;
import com.ngj.user.modle.Company;
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

    public Long addCompany(Company company) {

        Company old = mapper.selectByName(company.getName());
        if(old != null){//如果公司名称已经存在,那么直接返回-1
            return -1l;
        }
        return mapper.insertCompany(company);
    }

    public void changeCompany(Company company) {
        Company old = mapper.selectById(company.getId());
        PropertiesCopyUtils.copyNotNullProperties(old,company);
        mapper.updateCompany(company);
    }

    public List<Company> listCompany(Long start, Integer limit) {
        return mapper.listCompany(start,limit);
    }

    public Company findCompany(Long id) {
        return mapper.selectById(id);
    }

    public Company findCompanyByName(String name) {
        return mapper.selectByName(name);
    }

    public Company findCompanyByDomin(String domin) {
        return mapper.selectCompanyByDomain(domin);
    }
}
