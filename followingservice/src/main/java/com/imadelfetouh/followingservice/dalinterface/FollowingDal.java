package com.imadelfetouh.followingservice.dalinterface;

import com.imadelfetouh.followingservice.dal.queryexecuter.FType;
import com.imadelfetouh.followingservice.model.dto.UserDTO;
import com.imadelfetouh.followingservice.model.response.ResponseModel;

import java.util.List;

public interface FollowingDal {

    ResponseModel<List<UserDTO>> getFollowers(String userId, FType fType);
    ResponseModel<Void> addFollowing(String userId, String followingId);
    ResponseModel<Void> unfollow(String userId, String followingId);
}
