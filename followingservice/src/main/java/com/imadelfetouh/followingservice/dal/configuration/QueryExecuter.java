package com.imadelfetouh.followingservice.dal.configuration;

import com.imadelfetouh.followingservice.model.response.ResponseModel;
import org.hibernate.Session;

public interface QueryExecuter<T> {

    ResponseModel<T> executeQuery(Session session);
}
