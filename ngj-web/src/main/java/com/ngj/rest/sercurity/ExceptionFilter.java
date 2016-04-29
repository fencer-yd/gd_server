package com.ngj.rest.sercurity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by guanxinquan on 16/3/3.
 * 捕获所有服务端抛出的异常,并将异常转换为500 返回给客户端
 */
public class ExceptionFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        try {
            //这里是埋点,用于将req和resp放入ThreadLocal中,方便security获取
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpRequestThreadLocal.setContext(req);
            HttpResponseThreadLocal.setContext(resp);
            filterChain.doFilter(request, response);
        }catch (Exception e){
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }finally {
            //在调用结束时,要清理掉对应的ThreadLocal
            HttpRequestThreadLocal.removeContext();
            HttpResponseThreadLocal.removeContext();
        }
    }

    public void destroy() {

    }
}
