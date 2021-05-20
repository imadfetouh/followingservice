package com.imadelfetouh.followingservice.dalinterface;

import com.imadelfetouh.followingservice.dal.queryexecuter.FType;
import com.imadelfetouh.followingservice.model.dto.FollowingDTO;
import com.imadelfetouh.followingservice.model.response.ResponseModel;

import java.util.List;

public interface FollowingDal {

    ResponseModel<List<FollowingDTO>> getFollowers(String userId, FType fType);
    ResponseModel<Void> addFollowing(String userId, String followingId);
    ResponseModel<Void> unfollow(String userId, String followingId);
}
