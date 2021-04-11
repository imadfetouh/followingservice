package com.imadelfetouh.followingservice.setup;

import com.imadelfetouh.followingservice.dal.configuration.Executer;
import com.imadelfetouh.followingservice.dal.configuration.SessionType;
import com.imadelfetouh.followingservice.dal.db.FollowingDalDB;
import com.imadelfetouh.followingservice.dalinterface.FollowingDal;
import com.imadelfetouh.followingservice.model.dto.FollowingDTO;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
public class FollowingDalDBTest {

    @BeforeAll
    public static void setUpDatabase() {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        executer.execute(new SetupUserDBExecuter());
    }

    @Test
    public void testUserGetFollowersCorrect() {
        FollowingDal followingDal = new FollowingDalDB();
        ResponseModel<List<FollowingDTO>> responseModel = followingDal.getFollowingUsers("123");

        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());
        Assertions.assertEquals(1, responseModel.getData().size());
    }

    @Test
    public void testUserGetFollowersEmpty() {
        FollowingDal followingDal = new FollowingDalDB();
        ResponseModel<List<FollowingDTO>> responseModel = followingDal.getFollowingUsers("1234");

        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());
        Assertions.assertEquals(0, responseModel.getData().size());
    }
}
