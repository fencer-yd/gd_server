package com.ngj.rest.endpoint;

import com.ngj.reletionTable.GroupUserService;
import com.ngj.rest.req.LoginReq;
import com.ngj.rest.req.UserCreateReq;
import com.ngj.rest.req.UserUpdateAdminReq;
import com.ngj.rest.req.UserUpdateReq;
import com.ngj.rest.resp.CommonsResp;
import com.ngj.rest.resp.CommonsRespList;
import com.ngj.user.UserService;
import com.ngj.user.modle.User;
import com.ngj.menu.model.Menu;
import com.ngj.user.modle.UserPassport;
import com.ngj.utils.CipherUtil;
import com.ngj.utils.PropertiesCopyUtils;
import com.wordnik.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.json4s.jackson.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
    @Autowired
    private GroupUserService groupUserService;


    @POST
   @ApiOperation("创建新用户")
   @ApiResponses({
           @ApiResponse(code = 200,message = "用户id,如果id大于0表示创建成功,id=-1表示有重名,-2表示权限不在指定列表(只能设置两种权限ROLE_USER和ROLE_ADMIN",response = Long.class)
   })
   @RolesAllowed("ROLE_ADMIN")
    public CommonsResp<Long> addUser(UserCreateReq user,@Context Long tenant){
        Long cruTime = System.currentTimeMillis();
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setUsername(user.getUsername());
        newUser.setMobile(user.getMobile());
        newUser.setPassword(CipherUtil.generatePassword(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setTenant(tenant);
        newUser.setCTime(cruTime);
        newUser.setUTime(cruTime);
        newUser.setStatus(1);
       return new CommonsResp<>(userService.createUser(newUser));
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
            User newUser = new User();
            oldUser.setName(userReq.getName());
            oldUser.setUsername(userReq.getUsername());
            oldUser.setMobile(userReq.getMobile());
            oldUser.setPassword(CipherUtil.generatePassword(userReq.getPassword()));
            oldUser.setRole(userReq.getRole());
            newUser.setUTime(System.currentTimeMillis());
            userService.updateUser(oldUser);
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
            return new CommonsResp<>(user,0);
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
    public CommonsResp<User> getUsers(@ApiParam(required = false,defaultValue = "0")@QueryParam("start") Long start,
                                          @ApiParam(required = false,defaultValue = "200")@QueryParam("limit")Integer limit, @Context Long tenant){
        if(start == null)
            start = 0l;
        if(limit == null){
            limit =200;
        }
        List<User> users = userService.loadUsers(tenant, start, limit);
        if(start.longValue() == 0l){
            Integer total = userService.countUsers(tenant);
            return new CommonsResp(users,total);
        }else{
            return new CommonsResp(users);
        }
    }
    @GET
    @ApiOperation("获取用户列表")
    @ApiResponses({
            @ApiResponse(code = 200,message = "获取用户员工列表",response = User.class)
    })
    @Path("/list/group")
    @RolesAllowed("ROLE_USER")
    public CommonsResp<User> getUserByGroup(@QueryParam("groupId")Long groupId){
        List<User> users = userService.loadUsersBygroupId(groupId);
        if (!CollectionUtils.isEmpty(users)){
            Integer total = users.size();
            return new CommonsResp(users,total);
        }else{
            return new CommonsResp(users);
        }
    }

    @GET
    @ApiOperation("获取用户菜单列表")
    @ApiResponses({
            @ApiResponse(code = 200,message = "成功用户菜单列表",response = Json.class),
            @ApiResponse(code = 201,message = "获取用户菜单列表失败",response = Json.class)
    })
    @Path("/getMenus")
    @RolesAllowed("ROLE_USER")
    public CommonsResp<Menu> getMenuByUser(@QueryParam("userId") Long userId)
    {

        List<Menu> menus = userService.getMenusByUserId(userId);
        if(CollectionUtils.isEmpty(menus))
            return new CommonsResp(null,201,"获取目录菜单失败！");
        return new CommonsResp(menus,200);
    }
    @DELETE
    @ApiOperation("删除用户")
    @ApiResponses({
            @ApiResponse(code=200,message = "删除成功",response = Long.class),
            @ApiResponse(code=201,message = "删除失败",response = Long.class)
    })
    @RolesAllowed("ROLE_ADMIN")
    public Response delete(HttpRequest request,@QueryParam("id")Long id) throws DataAccessException {
        userService.deleteById(id);
        groupUserService.deleteByUserId(id);
        return Response.ok(new CommonsResp<>()).build();
    }
}
