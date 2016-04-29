package com.ngj.datasource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngj.resource.ResourceLoadUtils;
import com.ngj.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by guanxinquan on 16/3/1.
 */
@Slf4j
public abstract class RedisClient {

    static final String REDIS_FILE = "redis.cfg";

    protected ObjectMapper mapper = JsonUtils.getMapper();

    public abstract void set(String key,Object value);

    public abstract <T> T get(String key,Class<T> type);

    public abstract void del(String key);

    public abstract void close()throws IOException;


    public String generateKey(String prefix,Object originKey){
        if(originKey == null){
            return null;
        }
        return prefix+originKey;
    }


    protected Set<HostAndPort> loadHostAndPort() throws Exception {
        Properties p = ResourceLoadUtils.loadProperties(REDIS_FILE);

        String nodes = p.getProperty("nodes");
        if(StringUtils.isEmpty(nodes)){
            throw new Exception("redis nodes is empty");
        }

        Set<HostAndPort> redisHosts = new HashSet<HostAndPort>();
        String[] hosts = nodes.split(",");

        for(String host:hosts){
            String[] hostPort = host.split(":");
            if(hostPort.length < 2){
                redisHosts.add(new HostAndPort(hostPort[0],6379));
            }else{
                redisHosts.add(new HostAndPort(hostPort[0],Integer.valueOf(hostPort[1])));
            }
        }

        if (redisHosts.size() < 1){
            throw new Exception("redis nodes is empty");
        }

        log.info("redis started connected to {}",nodes);
        return redisHosts;
    }

}
