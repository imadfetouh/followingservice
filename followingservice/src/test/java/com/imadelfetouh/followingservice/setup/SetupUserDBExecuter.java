package com.imadelfetouh.followingservice.setup;

import com.imadelfetouh.followingservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.followingservice.dal.ormmodel.Following;
import com.imadelfetouh.followingservice.dal.ormmodel.User;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import org.hibernate.Session;

public class SetupUserDBExecuter implements QueryExecuter<Void> {

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        User user = new User("123", "imad", "imad.jpg");
        User user1 = new User("1234", "peter", "peter.jpg");

        Following following = new Following(user, user1);

        session.persist(user);
        session.persist(user1);
        session.persist(following);

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
