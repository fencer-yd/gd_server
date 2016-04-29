package com.ngj.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by pangyueqiang on 16/3/1.
 * 用户简化串行化
 */
public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static final ObjectMapper getMapper(){
        return mapper;
    }

    public static final String writeObjectAsString(Object obj){
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
