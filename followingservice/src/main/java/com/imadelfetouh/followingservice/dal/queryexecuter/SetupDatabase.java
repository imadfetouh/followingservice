package com.imadelfetouh.followingservice.dal.queryexecuter;

import com.imadelfetouh.followingservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import org.hibernate.Session;

public class SetupDatabase implements QueryExecuter<Void> {
    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        return new ResponseModel<>();
    }
}
