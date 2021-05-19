package com.imadelfetouh.followingservice.dal.queryexecuter;

import com.imadelfetouh.followingservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.followingservice.dal.ormmodel.User;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import org.hibernate.Session;

public class SetupDatabase implements QueryExecuter<Void> {
    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();
        User user1 = new User("u123", "imad", "imad.jpg");
        User user2 = new User("u1234", "peter", "peter.jpg");

        session.persist(user1);
        session.persist(user2);

        session.getTransaction().commit();

        return responseModel;
    }
}
