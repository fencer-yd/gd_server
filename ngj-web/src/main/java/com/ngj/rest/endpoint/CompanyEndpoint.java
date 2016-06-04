package com.ngj.rest.endpoint;

import com.ngj.customer.model.Customer;
import com.ngj.rest.req.CompanyChangeReq;
import com.ngj.rest.req.CompanyCreateReq;
import com.ngj.rest.resp.CommonsResp;
import com.ngj.user.CompanyService;
import com.ngj.user.modle.Company;
import com.ngj.user.modle.UserPassport;
import com.ngj.utils.PropertiesCopyUtils;
import com.wordnik.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by guanxinquan on 16/3/3.
 */
@Api("company")
@Path("/company")
@Consumes(MediaType.APPLICATION_JSON)
@Component("company")
@RolesAllowed("ROLE_SUP")
public class CompanyEndpoint {

    @Autowired
    private CompanyService companyService;

    @POST
    @ApiOperation("添加公司")
    @ApiResponses({
        @ApiResponse(code = 200,message = "公司添加成功",response = CommonsResp.class)
    })
    @RolesAllowed("ROLE_ADMIN")
    public CommonsResp<Long> addCompany(
            @Context UserPassport userPassport,
            CompanyCreateReq company){
        return new CommonsResp<>(companyService.addCompany(PropertiesCopyUtils.copyProperties(Company.class, company)));
    }

    @PUT
    @ApiOperation("修改公司")
    @ApiResponses({
            @ApiResponse(code = 200,message = "公司修改成功",response = Void.class)
    })
    @RolesAllowed("ROLE_ADMIN")
    public CommonsResp changeCompany(CompanyChangeReq companyRes){
        Company company = new Company();
        company.setId(companyRes.getId());
        company.setName(companyRes.getName());
        Customer customer = new Customer();
        customer.setTenant(companyRes.getId());
        customer.setName(companyRes.getName());
        customer.setDescrible(companyRes.getDescrible());
        customer.setContact(companyRes.getContact());
        customer.setAddress(companyRes.getAddress());
        customer.setSize(companyRes.getSize());
        companyService.changeCompany(company);
        companyService.changeCustomer(customer);
        return new CommonsResp(null,200);

    }
    @GET
    @ApiOperation("获取公司列表")
    @RolesAllowed("ROLE_USER")
    public CommonsResp listCompany() {
        List<Company> companyList = companyService.listCompany();
        return new CommonsResp(companyList, 200);
    }
    @GET
    @ApiOperation("获取公司")
    @RolesAllowed("ROLE_USER")
    @Path("/byId")
    public CommonsResp findCompany(@ApiParam(required = true)@QueryParam("id") Long id){
        Company company =  companyService.findCompany(id);
        return new CommonsResp(company,200);
    }
}
