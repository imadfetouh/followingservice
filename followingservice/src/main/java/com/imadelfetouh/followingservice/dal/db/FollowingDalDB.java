package com.imadelfetouh.followingservice.dal.db;

import com.imadelfetouh.followingservice.dal.configuration.Executer;
import com.imadelfetouh.followingservice.dal.configuration.SessionType;
import com.imadelfetouh.followingservice.dal.queryexecuter.*;
import com.imadelfetouh.followingservice.dalinterface.FollowingDal;
import com.imadelfetouh.followingservice.model.dto.UserDTO;
import com.imadelfetouh.followingservice.model.dto.NewFollowingDTO;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import com.imadelfetouh.followingservice.rabbit.RabbitProducer;
import com.imadelfetouh.followingservice.rabbit.producer.AddFollowingProducer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowingDalDB implements FollowingDal {

    @Override
    public ResponseModel<List<UserDTO>> getFollowers(String userId, FType fType) {
        Executer<List<UserDTO>> executer = new Executer<>(SessionType.READ);
        return executer.execute(new GetFollowersExecuter(userId, fType));
    }

    @Override
    public ResponseModel<Void> addFollowing(String userId, String followingId) {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        ResponseModel<Void> responseModel = executer.execute(new AddFollowingExecuter(userId, followingId));

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            NewFollowingDTO newFollowingDTO = new NewFollowingDTO(userId, followingId);
            RabbitProducer rabbitProducer = new RabbitProducer();
            rabbitProducer.produce(new AddFollowingProducer(newFollowingDTO));
        }

        return responseModel;
    }

    @Override
    public ResponseModel<Void> unfollow(String userId, String followingId) {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        return executer.execute(new UnfollowExecuter(userId, followingId));
    }
}
