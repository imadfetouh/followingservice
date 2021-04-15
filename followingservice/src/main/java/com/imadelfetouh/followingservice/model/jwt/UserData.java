package com.imadelfetouh.followingservice.model.jwt;

import java.io.Serializable;

public class UserData implements Serializable {

    private String userId;
    private String username;

    public UserData() {

    }

    public UserData(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
