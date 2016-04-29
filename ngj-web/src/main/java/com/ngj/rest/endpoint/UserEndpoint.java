package com.ngj.rest.endpoint;

import com.ngj.rest.req.UserCreateReq;
import com.ngj.rest.resp.CommonsResp;
import com.ngj.rest.resp.CommonsRespList;
import com.ngj.rest.req.LoginReq;
import com.ngj.rest.req.UserUpdateAdminReq;
import com.ngj.rest.req.UserUpdateReq;
import com.ngj.user.UserService;
import com.ngj.user.modle.User;
import com.ngj.user.modle.UserPassport;
import com.ngj.utils.JsonUtils;
import com.ngj.utils.PropertiesCopyUtils;
import com.wordnik.swagger.annotations.*;
import org.json4s.jackson.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

/**
 * Created by guanxinquan on 16/2/29.
 */
@Api("user")
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Component("user")
public class UserEndpoint {

    @Autowired
    private UserService userService;


    @POST
   @ApiOperation("创建新用户")
   @ApiResponses({
           @ApiResponse(code = 200,message = "用户id,如果id大于0表示创建成功,id=-1表示有重名,-2表示权限不在指定列表(只能设置两种权限ROLE_USER和ROLE_ADMIN",response = Long.class)
   })
   @RolesAllowed("ROLE_ADMIN")
    public CommonsResp<Long> addUser(UserCreateReq user,@Context Long tenant){
       return new CommonsResp<>(userService.createUser(PropertiesCopyUtils.copyProperties(User.class,user,"tenant",tenant)));
    }

    @PUT
    @Path("/admin")
    @ApiOperation("管理员修改用户信息")
    @ApiResponses({
            @ApiResponse(code = 200,message = "修改成功",response = UserPassport.class),
            @ApiResponse(code = 403,message = "无权修改",response = Void.class)
    })
    @RolesAllowed("ROLE_ADMIN")
    public Response changeUserByAdmin(UserUpdateAdminReq userReq,@Context Long tenant){

        User oldUser = userService.loadUser(userReq.getId());
        if(tenant.equals(oldUser.getTenant())){
            if(!User.checkRoles(userReq.getRole())){//如果修改权限,那么必须在修改范围之内
                return Response.noContent().status(Response.Status.FORBIDDEN).build();
            }
            userService.updateUser(PropertiesCopyUtils.copyProperties(User.class,userReq));
            return Response.ok(new CommonsResp<>()).build();
        }else{
            return Response.noContent().status(Response.Status.FORBIDDEN).build();
        }
    }

    @PUT
    @Path("/user")
    @ApiOperation("用户自己修改自己的信息")
    @ApiResponses({
            @ApiResponse(code = 200,message = "修改成功",response = UserPassport.class)
    })
    public UserPassport changeUser(UserUpdateReq user,@Context Long userId,@Context HttpServletResponse response){

        UserPassport newPassport = userService.updateUser(PropertiesCopyUtils.copyProperties(User.class, user, "id", userId));
        Cookie jsessionid = new Cookie("JSESSIONID", newPassport.getToken());
        response.addCookie(jsessionid);
        return newPassport;

    }
    @POST
    @ApiOperation("用户登陆")
    @ApiResponses({
            @ApiResponse(code = 200,message = "用户登陆,code -1表示用户名密码失败",response = UserPassport.class)
    })
    @Path("/login")
    public CommonsResp<UserPassport> login( LoginReq loginReq,@Context HttpServletResponse response){
        if(StringUtils.isEmpty(loginReq.getUsername()) || StringUtils.isEmpty(loginReq.getPassword()) || StringUtils.isEmpty(loginReq.getDomain())){
            return new CommonsResp<>(null,-1,"用户名或者密码错误");
        }
        UserPassport user = userService.userLogin(loginReq.getUsername(),loginReq.getDomain(),loginReq.getPassword());

        if(user != null) {
            Cookie jsessionid = new Cookie("JSESSIONID", user.getToken());
            jsessionid.setPath("/");
            response.addCookie(jsessionid);
            return new CommonsResp<>(user);
        }else{
            return new CommonsResp<>(null,-1,"用户名或者密码错误");
        }
    }

    @GET
    @ApiOperation("获取用户列表")
    @ApiResponses({
            @ApiResponse(code = 200,message = "获取用户员工列表",response = User.class)
    })
    @Path("/list")
    @RolesAllowed("ROLE_ADMIN")
    public CommonsRespList<User> getUsers(@ApiParam(required = false,defaultValue = "0")@QueryParam("start") Long start,
                                          @ApiParam(required = false,defaultValue = "200")@QueryParam("limit")Integer limit, @Context Long tenant){
        if(start == null)
            start = 0l;
        if(limit == null){
            limit =200;
        }
        List<User> users = userService.loadUsers(tenant, start, limit);
        if(start.longValue() == 0l){
            Integer total = userService.countUsers(tenant);
            return new CommonsRespList<>(users,total);
        }else{
            return new CommonsRespList<>(users);
        }
    }

    @GET
    @ApiOperation("获取用户菜单列表")
    @ApiResponses({
            @ApiResponse(code = 200,message = "获取用户菜单列表",response = Json.class)
    })
    @Path("/menulist")
    @RolesAllowed("ROLE_USER")
    public CommonsRespList<Menu> getMenuByUser(HttpServletRequest request){
        return new CommonsRespList<>();
    }
}
