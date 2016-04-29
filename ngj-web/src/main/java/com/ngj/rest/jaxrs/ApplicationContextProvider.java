package com.ngj.rest.jaxrs;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by 葫芦娃 on 2016/3/5.
 * 被{@link JerseyResource}使用,在jersey初始化过程中,获取spring中已经装配好的bean
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }
}
