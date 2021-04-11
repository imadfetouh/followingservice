package com.imadelfetouh.followingservice.dal.queryexecuter;

import com.imadelfetouh.followingservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;

public class UnfollowExecuter implements QueryExecuter<Void> {

    private String userId;
    private String followingId;

    public UnfollowExecuter(String userId, String followingId) {
        this.userId = userId;
        this.followingId = followingId;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        Query query = session.createQuery("DELETE FROM Following f WHERE f.user.userId = :userId AND f.userFollowing.userId = :followingId");
        query.setParameter("userId", userId);
        query.setParameter("followingId", followingId);

        query.executeUpdate();

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
