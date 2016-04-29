package com.ngj.rest.swagger;


import com.ngj.user.modle.UserPassport;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.FilterFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.BasicAuth;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by guanxinquan on 16/3/2.
 * swagger configuration
 */

@Component
@Data
public class SwaggerConfig {
    @Value("${swagger.resourcePackage}")
    private String resourcePackage;

    @Value("${swagger.basePath}")
    private String basePath;

    @Value("${swagger.apiVersion}")
    private String apiVersion;

    @PostConstruct
    public void init() {
        final ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
        scanner.setResourcePackage(resourcePackage);

        ScannerFactory.setScanner(scanner);
        ClassReaders.setReader(new DefaultJaxrsApiReader());
        ModelConverters.addConverter(new AccessHiddenModelConverter(), true);
        FilterFactory.setFilter(new AccessHiddenSpecFilter());

        final com.wordnik.swagger.config.SwaggerConfig config = ConfigFactory.config();
        config.setApiVersion(apiVersion);
        config.setBasePath(basePath);
    }


}
