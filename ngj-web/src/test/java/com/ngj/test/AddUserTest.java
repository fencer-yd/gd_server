package com.ngj.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngj.user.modle.User;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by guanxinquan on 16/2/29.
 */
public class AddUserTest {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws URISyntaxException, IOException {
        URI uri = new URIBuilder("http://localhost:12306/api/user/add").build();
        User user = new User();
        user.setPassword("123");
        user.setUsername("test");
        user.setMobile("123456");
        user.setName("test");
        user.setRole("ROLE_USER");
        user.setTenant(1l);
        user.setUTime(System.currentTimeMillis());
        user.setCTime(System.currentTimeMillis());
        HttpPost post = new HttpPost(uri);
        post.setHeader("Authorization","BBGADEg..");
        StringEntity stringEntity = new StringEntity(mapper.writeValueAsString(user),ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);
        ClientConnectionPool clientConnectionPool = new ClientConnectionPool();
        String result = clientConnectionPool.execute(post);
        System.out.println(result);

    }

}
