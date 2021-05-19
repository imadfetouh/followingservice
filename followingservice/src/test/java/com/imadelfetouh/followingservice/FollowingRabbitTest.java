package com.imadelfetouh.followingservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imadelfetouh.followingservice.dal.configuration.Executer;
import com.imadelfetouh.followingservice.dal.configuration.SessionType;
import com.imadelfetouh.followingservice.dal.db.FollowingDalDB;
import com.imadelfetouh.followingservice.dal.queryexecuter.SetupDatabase;
import com.imadelfetouh.followingservice.model.jwt.UserData;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import com.imadelfetouh.followingservice.rabbit.RabbitConfiguration;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FollowingRabbitTest {

    static String getJWT() {
        Gson gson = new Gson();
        UserData userData = new UserData("u123", "imad", "USER");;
        Map<String, String> claims = new HashMap<>();
        claims.put("userdata", gson.toJson(userData));
        return CreateJWTToken.getInstance().create(claims);
    }

    ResponseEntity<String> getFollowersRequest(String url) {
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyZGF0YSI6IntcInVzZXJJZFwiOlwidTEyM1wiLFwidXNlcm5hbWVcIjpcImltYWRcIixcInJvbGVcIjpcIlVTRVJcIn0iLCJpc3MiOiJLd2V0dGVyaW1hZCIsImlhdCI6MTYyMTQ0NDUzOCwiZXhwIjoxNjIxNDQ4MTM4fQ.9JzLxYxKjnzZJmUeawkbqPTiCsiMFgj7da5zV3k3R2A";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "jwt-token="+jwtToken);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        return responseEntity;
    }

    @BeforeAll
    static void setupDatabase() {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        executer.execute(new SetupDatabase());

        Channel channel = RabbitConfiguration.getInstance().getChannel();
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().correlationId("testcorr").build();

        try {
            String user1 = new JSONObject()
                    .put("userId", "u123").put("username", "imad").put("password", "imad").put("role", "USER").put("photo", "imad.jpg")
                    .put("profile", new JSONObject().put("profileId", "p123").put("bio", "Hello").put("location", "Helmond").put("website", "imad.nl")).toString();

            String user2 = new JSONObject()
                    .put("userId", "u1234").put("username", "peter").put("password", "peter").put("role", "USER").put("photo", "peter.jpg")
                    .put("profile", new JSONObject().put("profileId", "p1234").put("bio", "Hello").put("location", "Helmond").put("website", "peter.nl")).toString();

            channel.basicPublish("adduserexchange", "", properties, user1.getBytes());
            channel.basicPublish("adduserexchange", "", properties, user2.getBytes());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    @Order(1)
    void getFollowersFromProfileServiceEmptyList() {
        Gson gson = new Gson();
        String url = "http://localhost:8089/profile/u1234";

        ResponseEntity<String> responseEntity = getFollowersRequest(url);

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());

        JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);
        Long followers = jsonObject.get("followers").getAsLong();

        Assertions.assertEquals(0, followers);
    }

    @Test
    @Order(2)
    void addFollower() {
        FollowingDalDB followingDalDB = new FollowingDalDB();

        ResponseModel<Void> responseModel = followingDalDB.addFollowing("u123", "u1234");

        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());
    }

    @Test
    @Order(3)
    void getFollowersFromProfileServiceNonEmptyList() throws InterruptedException {
        Thread.sleep(3000);
        Gson gson = new Gson();
        String url = "http://localhost:8089/profile/u1234";

        ResponseEntity<String> responseEntity = getFollowersRequest(url);

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());

        JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);
        System.out.println(jsonObject);
        Long followers = jsonObject.get("followers").getAsLong();

        Assertions.assertEquals(1, followers);
    }

    @Test
    @Order(4)
    void getTestUserFollowBoolean() {
        Gson gson = new Gson();
        String url = "http://localhost:8089/profile/u123";

        ResponseEntity<String> responseEntity = getFollowersRequest(url);

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());

        JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);
        boolean follow = jsonObject.get("follow").getAsBoolean();

        Assertions.assertTrue(follow);
    }
}
