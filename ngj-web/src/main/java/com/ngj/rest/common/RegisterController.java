package com.ngj.rest.common;

import com.ngj.register.RegisterService;
import com.ngj.register.model.Register;
import com.ngj.rest.req.RegisterReq;
import com.ngj.rest.resp.CommonsResp;
import com.ngj.user.CompanyService;
import com.ngj.utils.PropertiesCopyUtils;
import com.wordnik.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by pangyueqiang on 16/4/20.
 */
@Api("register")
@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Component("register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private CompanyService companyService;

    @POST
    @Path("/create")
    @ApiOperation("注册公司和管理员")
    @ApiResponses({
            @ApiResponse(code = 200,message = "注册成功",response = CommonsResp.class)
    })
    public Response register(
            RegisterReq registerReq) throws IOException {
        registerService.register(PropertiesCopyUtils.copyProperties(Register.class, registerReq));
        return Response.ok().build();
    }
    @GET
    @Path("/validatorname")
    @ApiOperation("验证公司名称是否已注册")
    @ApiResponses({
            @ApiResponse(code = 200,message = "可以注册",response = CommonsResp.class),
            @ApiResponse(code = 201,message = "已经注册",response = CommonsResp.class)
    })

    public CommonsResp checkCompanyName(@ApiParam(required = true)@QueryParam("name") String name){
        if(companyService.findCompanyByName(name)!=null)
        {
            return new CommonsResp<>(null,201,"该公司已经注册");
        }else
        {
            return new CommonsResp<>(null,200,"可以注册");
        }

    }
    @GET
    @Path("/validatordomin")
    @ApiOperation("验证域名")
    @ApiResponses({
            @ApiResponse(code = 200,message = "可以注册",response = CommonsResp.class),
            @ApiResponse(code = 201,message = "已经注册",response = CommonsResp.class)
    })
    public CommonsResp checkDomin(@ApiParam(required = true)@QueryParam("domain") String domin){
        if(companyService.findCompanyByDomin(domin)!=null)
        {
            return new CommonsResp<>(null,201,"域名已经注册");
        }else
        {
            return new CommonsResp<>(null,200,"可以注册");
        }
    }
}
