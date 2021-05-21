package com.imadelfetouh.followingservice.dal.queryexecuter;

import com.imadelfetouh.followingservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.followingservice.model.dto.UserDTO;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class GetFollowingExecuter implements QueryExecuter<List<UserDTO>> {

    private String userId;

    public GetFollowingExecuter(String userId) {
        this.userId = userId;
    }

    @Override
    public ResponseModel<List<UserDTO>> executeQuery(Session session) {
        ResponseModel<List<UserDTO>> responseModel = new ResponseModel<>();

        Query query = session.createQuery("SELECT new com.imadelfetouh.followingservice.model.dto.UserDTO(f.userFollowing.userId, f.userFollowing.username, f.userFollowing.photo) FROM Following f WHERE f.user.userId = :userId");
        query.setParameter("userId", userId);
        List<UserDTO> userDTOS = query.getResultList();

        responseModel.setResponseType(ResponseType.CORRECT);
        responseModel.setData(userDTOS);

        return responseModel;
    }
}
