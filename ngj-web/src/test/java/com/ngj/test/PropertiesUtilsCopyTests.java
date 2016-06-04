package com.ngj.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ngj.rest.req.LoginReq;
import com.ngj.user.modle.User;
import com.ngj.utils.JsonUtils;
import com.ngj.utils.PropertiesCopyUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by guanxinquan on 16/3/9.
 */
public class PropertiesUtilsCopyTests {

    public static void main(String[] args) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
//        LoginReq loginReq = new LoginReq();
//        loginReq.setUsername("zhangsan");
//
//        User user = new User();
//        user.setUsername("lisi");
//        user.setPassword("123");
//        PropertiesCopyUtils.copyNotNullProperties(user,loginReq,"id",100);
//
//        User user2 = new User();
//        user2.setUsername("lisi");
//        user2.setPassword("123");
//        BeanUtils.copyProperties(user2,loginReq);
//
//        System.out.println(JsonUtils.getMapper().writeValueAsString(user));
//        System.out.println(JsonUtils.getMapper().writeValueAsString(user2));
            testSwitch();
    }
    private static void testSwitch()
    {
        int i = 4;
        switch (i){
            case 1:{
                System.out.println("case1");
                break;
            }
            case 4:{
                System.out.println("case4");
                return;

            }
            case 3:{
                System.out.println("case3");
            }
            case 2:{
                System.out.println("case2");
            }
        }
    }
}
