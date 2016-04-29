package com.ngj.resource;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by guanxinquan on 16/2/29.
 * 加载系统配置文件相关的资源
 */
@Slf4j
public class ResourceLoadUtils {

    private static String env = null;

    /**
     * 获取上下文环境变量
     * @return
     * @throws Exception
     */
    public synchronized static final String getEnv() throws Exception {
        if(env == null){
            String e = System.getProperty("env");
            if(e == null){
                log.error("data source env is empty !");
                throw new Exception("data source env is empty ");
            }else{
                if(e.equals("dev") || e.equals("test") || e.equals("online")){
                    log.info("service started at {}",e);
                    env = e;
                }else{
                    log.error("data source env config error {}",env);
                    throw new Exception("data source env config error");
                }
            }

        }
        return env;
    }


    public static final Properties loadProperties(String fileName) throws Exception {
        String e = getEnv();
        Properties properties = new Properties();
        InputStream inputStream = ResourceLoadUtils.class.getResourceAsStream("/" + e + "/" +fileName);
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }

}
