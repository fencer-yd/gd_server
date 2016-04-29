package com.ngj.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngj.rest.req.LoginReq;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by guanxinquan on 16/3/1.
 */
public class LoginTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws URISyntaxException, IOException {
        URI uri = new URIBuilder("http://localhost:8080/api/user/login").build();
        LoginReq loginReq = new LoginReq();
        loginReq.setPassword("abc");
        //loginReq.setTenant(1l);
        loginReq.setUsername("admin");
        HttpPost post = new HttpPost(uri);
        StringEntity stringEntity = new StringEntity(mapper.writeValueAsString(loginReq), ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);
        ClientConnectionPool clientConnectionPool = new ClientConnectionPool();
        String result = clientConnectionPool.execute(post);

        System.out.println(result);
    }
}
