package com.imadelfetouh.followingservice.model.dto;

public class FollowingDTO {

    private String userId;
    private String username;
    private String photo;

    public FollowingDTO(String userId, String username, String photo) {
        this.userId = userId;
        this.username = username;
        this.photo = photo;
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

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }
}
