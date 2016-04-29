package com.ngj.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ngj.resource.ResourceLoadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * Created by guanxinquan on 16/3/1.
 */
public class RedisSingleClient extends RedisClient {

    private static final Logger logger = LoggerFactory.getLogger(RedisSingleClient.class);

    private JedisPool pool;

    private Integer TIME_OUT = 1000*60;


    public RedisSingleClient() throws Exception {
        Set<HostAndPort> hostAndPorts =  loadHostAndPort();
        if(hostAndPorts.size() >= 1){
            HostAndPort hp = (HostAndPort) hostAndPorts.toArray()[0];
            pool = new JedisPool(new JedisPoolConfig(),hp.getHost(),hp.getPort(),TIME_OUT);
        }else{
            throw new Exception("redis connection is empty");
        }
    }

    public void set(String key, Object value) {
        Jedis jedis = pool.getResource();
        if(value == null){
            return ;
        }
        try {
            String str = mapper.writeValueAsString(value);
            jedis.set(key,str);
        } catch (JsonProcessingException e) {
            logger.error("json process exception ", e);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public <T> T get(String key, Class<T> type) {
        Jedis jedis = pool.getResource();
        try{
            String result = jedis.get(key);
            if(result == null || "".equals(result)){
                return null;
            }
            return mapper.readValue(result,type);
        } catch (IOException e) {
            logger.error("json process exception ",e);
            return null;
        }finally {
            if(jedis != null)
                jedis.close();
        }

    }

    public void del(String key) {
        Jedis jedis = pool.getResource();
        try{
            jedis.del(key);
        }catch (Exception e){

        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public void close() {
        pool.destroy();
    }


}
