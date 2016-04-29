package com.ngj.rest.sercurity;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by guanxinquan on 16/3/11.
 */
public class HttpResponseThreadLocal {

    private static final ThreadLocal<HttpServletResponse> context = new ThreadLocal<HttpServletResponse>();

    public static void setContext(HttpServletResponse request){
        context.set(request);
    }

    public static HttpServletResponse getContext(){
        return context.get();
    }

    public static void removeContext(){
        context.remove();
    }


}
