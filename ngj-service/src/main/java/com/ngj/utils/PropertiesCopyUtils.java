package com.ngj.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by guanxinquan on 16/3/7.
 * 属性拷贝
 */
@Slf4j
public class PropertiesCopyUtils {

    /**
     * 创建dest类型的instance,并将src中对应的属性拷贝到instance中(包括null值),最终返回instance
     * @param dest
     * @param src
     * @param others
     * @param <T>
     * @return
     */
    public static final <T> T copyProperties(Class<T> dest,Object src,Object ... others){
        T instance = null;
        try {
            instance = dest.newInstance();
            BeanUtils.copyProperties(instance, src);

            for(int i = 0 ; i < others.length ;i=i+2){
                BeanUtils.setProperty(instance, (String) others[i],others[i+1]);
            }


        }catch (Exception e){
            log.error("copy properties error",e);
        }
        return instance;
    }

    /**
     * 创建dest类型的instance,并将src中对应的属性拷贝到instance中(不包括null值),最终返回instance
     * @param dest
     * @param src
     * @param others
     * @param <T>
     * @return
     */
    public static final <T> T copyNotNullProperties(T dest,Object src,Object ... others) {
        try {
            org.springframework.beans.BeanUtils.copyProperties(src,dest,getNullPropertyNames(src));
            for(int i = 0 ; i < others.length ;i=i+2){
                BeanUtils.setProperty(dest, (String) others[i],others[i+1]);
            }
        }catch (Exception e){
            log.error("copy properties error",e);
        }
        return dest;
    }


    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}


