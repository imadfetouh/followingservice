package com.imadelfetouh.followingservice.dal.ormmodel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "following")
public class Following implements Serializable {

    public Following() {

    }

    public Following(User user, User userFollowing) {
        this.user = user;
        this.userFollowing = userFollowing;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", referencedColumnName = "userId")
    private User userFollowing;
}
