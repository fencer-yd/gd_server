package com.ngj.rest.swagger;

import com.wordnik.swagger.annotations.ApiModelProperty;
import com.wordnik.swagger.converter.SwaggerSchemaConverter;
import com.wordnik.swagger.model.Model;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import scala.Option;
import scala.collection.immutable.Map;

import javax.xml.bind.annotation.XmlElement;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Created by 葫芦娃 on 2016/3/5.
 * 解析swagger {@link ApiModelProperty}中的hidden属性
 */
@Slf4j
public class AccessHiddenModelConverter extends SwaggerSchemaConverter {

    public Option<Model> read(final Class<?> modelClass, final Map<String, String> typeMap) {
        final Option<Model> modelOption = super.read(modelClass, typeMap);

        Class<?> currentClass = modelClass;

        while (currentClass.getSuperclass() != null) {
            for (final Method method : modelClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(ApiModelProperty.class) && isAccessHidden(method)) {
                    hideModelProperty(currentClass, method, modelOption);
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return modelOption;
    }

    private boolean isAccessHidden(final Method method) {
        if (!method.getAnnotation(ApiModelProperty.class).access().isEmpty()) {
            return method.getAnnotation(ApiModelProperty.class).access().equals("hidden");
        }

        return false;
    }

    private void hideModelProperty(final Class<?> currentClass, final Method method, final Option<Model> modelOption) {
        final String propertyName;

        if (method.isAnnotationPresent(XmlElement.class) && !method.getAnnotation(XmlElement.class).name().isEmpty()) {
            propertyName = method.getAnnotation(XmlElement.class).name();
        } else {
            propertyName = getClassMemberName(currentClass, method);
        }

        if (StringUtils.hasText(propertyName)) {
            modelOption.get().properties().remove(propertyName);
            log.debug("Successfully hidden API model property '" + propertyName + "'");
        }
    }

    private String getClassMemberName(final Class<?> currentClass, final Method method) {
        try {
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(currentClass).getPropertyDescriptors()) {
                if (propertyDescriptor.getReadMethod().getName().equals(method.getName())) {
                    return propertyDescriptor.getName();
                }
            }
        } catch (IntrospectionException e) {
            log.error("Unable to retrieve field name", e);
        }

        return null;
    }
}
