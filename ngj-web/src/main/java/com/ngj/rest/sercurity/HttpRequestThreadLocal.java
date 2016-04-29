package com.ngj.rest.sercurity;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by guanxinquan on 16/3/11.
 */
public class HttpRequestThreadLocal {

    private static final ThreadLocal<HttpServletRequest> context = new ThreadLocal<HttpServletRequest>();

    public static void setContext(HttpServletRequest request){
        context.set(request);
    }

    public static HttpServletRequest getContext(){
        return context.get();
    }

    public static void removeContext(){
        context.remove();
    }

}
