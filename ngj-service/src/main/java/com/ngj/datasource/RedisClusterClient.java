package com.ngj.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngj.resource.ResourceLoadUtils;
import com.ngj.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by guanxinquan on 16/3/1.
 */
public class RedisClusterClient extends RedisClient{

    private static final Logger logger = LoggerFactory.getLogger(RedisClusterClient.class);

    private JedisCluster cluster;

    public RedisClusterClient() throws Exception {

        Set<HostAndPort> hostAndPorts = loadHostAndPort();

        cluster = new JedisCluster(hostAndPorts);
    }

    public void set(String key,Object value){
        if(value == null){
            return ;
        }
        try {
            String str = mapper.writeValueAsString(value);
            cluster.set(key,str);
        } catch (JsonProcessingException e) {
            logger.error("json process exception ", e);
        }
    }

    public <T> T get(String key,Class<T> type){
        String result = cluster.get(key);
        if(result == null || "".equals(result)){
            return null;
        }
        try {
            return mapper.readValue(result,type);
        } catch (IOException e) {
            logger.error("json process exception ",e);
        }
        return null;
    }

    public void del(String key){
        cluster.del(key);
    }



    @PreDestroy
    public void close() throws IOException {
        cluster.close();
    }
}
