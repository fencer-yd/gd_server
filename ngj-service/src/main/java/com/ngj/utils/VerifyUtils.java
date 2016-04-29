package com.ngj.utils;

/**
 * Created by guanxinquan on 16/3/9.
 */
public class VerifyUtils {

    /**
     * 验证所有传入参数不能为null,如果是string类型则不能为空字符串
     * @param objs
     * @return
     */
    public static boolean allNotNullOrEmpty(Object ... objs){
        for(Object obj : objs){
            if(obj == null)
                return false;

            if(obj instanceof String && "".equals(obj)){
                return false;
            }
        }
        return true;
    }

}
