package com.imadelfetouh.followingservice.dal.queryexecuter;

import com.imadelfetouh.followingservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.followingservice.dal.ormmodel.Following;
import com.imadelfetouh.followingservice.dal.ormmodel.User;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;

public class AddFollowingExecuter implements QueryExecuter<Void> {

    private String userId;
    private String followingId;

    public AddFollowingExecuter(String userId, String followingId) {
        this.userId = userId;
        this.followingId = followingId;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        Query query = session.createSQLQuery("INSERT INTO following (user_id, following_id) VALUES (:userId, :followingId)");
        query.setParameter("userId", userId);
        query.setParameter("followingId", followingId);

        query.executeUpdate();

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
