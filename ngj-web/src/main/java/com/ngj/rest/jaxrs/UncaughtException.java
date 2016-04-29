package com.ngj.rest.jaxrs;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by guanxinquan on 16/3/7.
 * jetty 处理过程中的异常
 */
@Slf4j
public class UncaughtException extends Throwable implements ExceptionMapper<Throwable>
{
    @Override
    public Response toResponse(Throwable throwable) {
        log.error("rest service error",throwable);
        return Response.status(500).entity("service error!!").type("text/plain").build();
    }
}
