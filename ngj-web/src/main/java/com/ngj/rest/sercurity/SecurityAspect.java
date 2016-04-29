package com.ngj.rest.sercurity;

import com.ngj.exception.PromptException;
import com.ngj.rest.resp.CommonsResp;
import com.ngj.user.UserPassportService;
import com.ngj.user.modle.UserPassport;
import com.ngj.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by guanxinquan on 16/3/4.
 * 验证用户权限以及日志生成
 */
@Slf4j(topic = "statistic")
@Component("securityAspect")
public class SecurityAspect {

    private static final Logger logger = LoggerFactory.getLogger("overtime");

    @Autowired
    private UserPassportService userService;

    @Autowired
    private List<AccessDecisionVoter<? extends Object>> decisionVoters;

    public Object doControllerAspect(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = HttpRequestThreadLocal.getContext();
        HttpServletResponse response = HttpResponseThreadLocal.getContext();

        String pathInfo = request.getPathInfo();
        if(pathInfo != null && (pathInfo.equals("/user/login")||pathInfo.startsWith("/api-docs")) ){
            return pjp.proceed();//如果是登录,那么直接返回
        }

        /**
         * 验证用户是否登录
         */
        UserPassport passport = checkUser(request,response);

        if(passport == null){
            response.sendError(401,"no login");
            return null;
        }

        /**
         * 验证用户是否有权限访问对应接口
         */
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        if(!checkAuth(pjp.getTarget().getClass().getAnnotations(),signature.getMethod().getAnnotations(),passport)){
            response.sendError(403,"no auth");
            return null;
        }

        /**
         * 设置userId或者tenant
         */
        String[] names = signature.getParameterNames();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        Object[] args = pjp.getArgs();

        //强制设置用户id,企业id和用户角色
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals("userId") && containsContext(parameterAnnotations[i])) {
                args[i] = passport.getId();
            } else if (names[i].equals("tenant") && containsContext(parameterAnnotations[i])) {
                args[i] = passport.getTenant();
            } else if (names[i].equals("role") && containsContext(parameterAnnotations[i])) {
                args[i] = passport.getRole();
            } else if(names[i].equals("passport") && containsContext(parameterAnnotations[i])){
                args[i] = passport;
            }
        }

        long start = System.currentTimeMillis();

        Object ret = null;

        try {
            ret = pjp.proceed(args);
        }catch (PromptException e){//拦截参数验证异常,并转换为code 10
            return new CommonsResp<>(ret,e.getCode(),e.getMessage());
        }

        //输出调用日志
        loggerInvokeInfo(passport,signature.getDeclaringTypeName(),signature.getName(),System.currentTimeMillis()-start,args);

        if(!(ret instanceof CommonsResp || ret instanceof Response)){
            return new CommonsResp<>(ret);
        }else {
            return ret;
        }

    }
    private  boolean containsContext(Annotation[] annotations){
        for(Annotation a : annotations){
            if(a instanceof Context){
                return true;
            }
        }
        return false;
    }

    /**
     * 验证已经登录的用户是否有权限访问对应的接口
     * <li> 先验证标记在方法上的{@link RolesAllowed},如果指定的权限允许(表示用户属于配置权限中的任意角色),返回true</li>
     * <li> 验证标记在class上的{@link RolesAllowed},如果指定的权限允许(表示用户属于配置权限中的任意角色),返回true</li>
     * <li> 如果方法或者classs上都没有{@link RolesAllowed},使用默认的ROLE_USER进行验证</li>
     *
     * @param classAnnotations
     * @param methodAnnotations
     * @param passport
     * @return
     */
    private boolean checkAuth(Annotation[] classAnnotations,Annotation[] methodAnnotations,UserPassport passport){

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if(!StringUtils.isEmpty(passport.getRole())) {

            String[] roles = passport.getRole().split(",");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(null,null,authorities);


        //验证method
        for(Annotation methodAnnotation : methodAnnotations){
            if(methodAnnotation instanceof RolesAllowed){
                RolesAllowed role = (RolesAllowed) methodAnnotation;
                String[] methodRole = role.value();
                if(methodRole != null && methodRole.length > 0){
                    if(doAuthCheck(methodRole,auth)){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }

        //验证class
        for(Annotation classAnnotation : classAnnotations){
            if(classAnnotation instanceof RolesAllowed){
                RolesAllowed role = (RolesAllowed) classAnnotation;
                String[] classRole = role.value();
                if(classRole != null && classRole.length > 0){
                    if(doAuthCheck(classRole,auth)){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }

        //验证default
        if(doAuthCheck(new String[]{"ROLE_USER"},auth)){
            return true;
        }else{
            return false;
        }
    }

    //这里使用具有层级权限关系的voter,详情看security.xml
    private boolean doAuthCheck(String[] roles,Authentication auth){
        for(String role : roles){
            for(AccessDecisionVoter voter : decisionVoters){
                int result = voter.vote(auth,null, Collections.singletonList(new SecurityConfig(role)));
                if (result == AccessDecisionVoter.ACCESS_GRANTED){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 验证用户token是否有效
     * @param req
     * @param resp
     * @return
     */
    private UserPassport checkUser(HttpServletRequest req,HttpServletResponse resp){

        String auth = req.getHeader("Authorization");
        String pathInfo = req.getPathInfo();

        if(StringUtils.isEmpty(auth)){
            Cookie[] cookies = req.getCookies();
            if(cookies != null){
                for (Cookie cookie : cookies){
                    String name = cookie.getName();
                    if(name != null && name.equals("JSESSIONID")){
                        auth = cookie.getValue();
                        break;
                    }
                }
            }
        }

        try {

            if (StringUtils.isEmpty(auth)) {
                logger.error("request with empty auth "+pathInfo);
                throw new BadCredentialsException("user token is exception");
            }

            return loadUser(auth);
        }catch (BadCredentialsException e){
            logger.error("user author error",e);
            return null;
        }
    }

    private UserPassport loadUser(String auth){
        try {
            UserPassport passport = userService.loadPassport(auth);
            if (passport != null) {
                if (!passport.getToken().equals(auth)) {
                    return null;
                }
            }
            return passport;
        }catch (Exception e){
            log.error("user token exception",e);
            return null;
        }
    }

    /**
     * 打印统计日志,包括访问统计和超时访问统计
     * @param passport
     * @param className
     * @param methodName
     * @param userdTime
     * @param args
     */
    private void loggerInvokeInfo(UserPassport passport,String className,String methodName,Long userdTime,Object[] args){
        Long userId = passport.getId();
        List<Object> printableArgs = new ArrayList<>();
        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof HttpServletResponse || arg instanceof HttpServletRequest || arg instanceof UserPassport) {

                } else {
                    printableArgs.add(arg);
                }
            }
        }

        log.info("{}||{}||{}||{}||{}", userId, className, methodName, JsonUtils.writeObjectAsString(printableArgs), userdTime);

        if (userdTime > 1000) {
            log.info("{}||{}||{}||{}||{}", userId, className, methodName, JsonUtils.writeObjectAsString(printableArgs), userdTime);
        }
    }
}
