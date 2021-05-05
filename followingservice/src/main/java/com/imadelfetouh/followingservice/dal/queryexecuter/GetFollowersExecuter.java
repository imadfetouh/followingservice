package com.imadelfetouh.followingservice.dal.queryexecuter;

import com.imadelfetouh.followingservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.followingservice.model.dto.FollowingDTO;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class GetFollowersExecuter implements QueryExecuter<List<FollowingDTO>> {

    private String userId;

    public GetFollowersExecuter(String userId) {
        this.userId = userId;
    }

    @Override
    public ResponseModel<List<FollowingDTO>> executeQuery(Session session) {
        ResponseModel<List<FollowingDTO>> responseModel = new ResponseModel<>();

        Query query = session.createQuery("SELECT new com.imadelfetouh.followingservice.model.dto.FollowingDTO(f.user.userId, f.user.username, f.user.photo) FROM Following f WHERE f.userFollowing.userId = :userId");
        query.setParameter("userId", userId);
        List<FollowingDTO> followingDTOS = query.getResultList();

        responseModel.setResponseType(ResponseType.CORRECT);
        responseModel.setData(followingDTOS);

        return responseModel;
    }
}
