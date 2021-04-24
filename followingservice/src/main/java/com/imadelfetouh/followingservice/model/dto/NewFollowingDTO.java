package com.imadelfetouh.followingservice.model.dto;

import java.io.Serializable;

public class NewFollowingDTO implements Serializable {

    private String userId;
    private String followingId;

    public NewFollowingDTO(String userId, String followingId) {
        this.userId = userId;
        this.followingId = followingId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setFollowingId(String followingId) {
        this.followingId = followingId;
    }

    public String getFollowingId() {
        return followingId;
    }
}
