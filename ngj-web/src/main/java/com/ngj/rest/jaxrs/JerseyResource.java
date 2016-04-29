package com.ngj.rest.jaxrs;

import com.ngj.user.modle.UserPassport;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import java.util.Map;

/**
 * Created by 葫芦娃 on 2016/3/5.
 * register information and register spring created bean to jersey (jax-rs)
 * register swagger information
 * config by web.xml
 */
@Slf4j
public class JerseyResource  extends ResourceConfig {

    public  JerseyResource(){

        //used for jersey jaxrs
        final String jacksonPackage = "org.codehaus.jackson.jaxrs";
        final String swaggerJaxrsJsonPackage = "com.wordnik.swagger.jaxrs.json";
        final String swaggerJaxrsListingPackage = "com.wordnik.swagger.jaxrs.listing";
        //register package
        packages(swaggerJaxrsJsonPackage, swaggerJaxrsListingPackage, jacksonPackage);

        // enable multipart
        register(MultiPartFeature.class);

        //未捕获的服务端异常
        register(UncaughtException.class);

        //register beans defined by spring with Path annotation (this is service)
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        Map<String, Object> services = context.getBeansWithAnnotation(Path.class);
        for(Map.Entry<String,Object> service : services.entrySet()){
            if(!(service.getValue() instanceof ApiListingResourceJSON)) {
                log.info("register service : {}",service.getKey());
                register(service.getValue());
            }
        }


    }


}
