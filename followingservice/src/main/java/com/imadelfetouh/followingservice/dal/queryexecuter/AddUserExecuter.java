package com.imadelfetouh.followingservice.dal.queryexecuter;

import com.imadelfetouh.followingservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.followingservice.dal.ormmodel.User;
import com.imadelfetouh.followingservice.model.dto.UserDTO;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import org.hibernate.Session;

public class AddUserExecuter implements QueryExecuter<Void> {

    private UserDTO userDTO;

    public AddUserExecuter(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        User user = new User(userDTO.getUserId(), userDTO.getUsername(), userDTO.getPhoto());

        session.persist(user);

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
