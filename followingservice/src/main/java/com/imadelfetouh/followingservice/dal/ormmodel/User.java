package com.imadelfetouh.followingservice.dal.ormmodel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {

    public User() {

    }

    public User(String userId, String username, String userphoto) {
        this.userId = userId;
        this.username = username;
        this.userphoto = userphoto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "userphoto")
    private String userphoto;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Following> followings;

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public List<Following> getFollowings() {
        return followings;
    }
}