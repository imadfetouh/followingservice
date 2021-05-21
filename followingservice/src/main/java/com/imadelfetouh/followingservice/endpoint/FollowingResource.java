package com.imadelfetouh.followingservice.endpoint;

import com.google.gson.Gson;
import com.imadelfetouh.followingservice.dal.queryexecuter.FType;
import com.imadelfetouh.followingservice.dalinterface.FollowingDal;
import com.imadelfetouh.followingservice.model.dto.UserDTO;
import com.imadelfetouh.followingservice.model.jwt.UserData;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("following")
public class FollowingResource {

    private static final Logger logger = Logger.getLogger(FollowingResource.class.getName());

    @Autowired
    private FollowingDal followingDal;

    @GetMapping(value = "/followings/{userId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getFollowingUsers(@PathVariable("userId") String userId) {

        logger.info("get followings request made");

        ResponseModel<List<UserDTO>> responseModel = followingDal.getFollowers(userId, FType.FOLLOWINGS);

        if(responseModel.getResponseType().equals(ResponseType.EMPTY)) {
            return ResponseEntity.noContent().build();
        }
        else if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().body(responseModel.getData());
        }

        return ResponseEntity.status(500).build();
    }

    @GetMapping(value = "/followers/{userId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getFollowers(@PathVariable("userId") String userId) {

        logger.info("get followers request made");

        ResponseModel<List<UserDTO>> responseModel = followingDal.getFollowers(userId, FType.FOLLOWERS);

        if(responseModel.getResponseType().equals(ResponseType.EMPTY)) {
            return ResponseEntity.noContent().build();
        }
        else if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().body(responseModel.getData());
        }

        return ResponseEntity.status(500).build();
    }

    @PostMapping("/{followingId}")
    public ResponseEntity<Void> addFollowing(@RequestAttribute("userdata") String userDataString, @PathVariable("followingId") String followingId) {
        logger.info("Add following request made with followingId: " + followingId);

        Gson gson = new Gson();
        UserData userData = gson.fromJson(userDataString, UserData.class);

        ResponseModel<Void> responseModel = followingDal.addFollowing(userData.getUserId(), followingId);

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(500).build();
    }

    @DeleteMapping("/{followingId}")
    public ResponseEntity<Void> unfollow(@RequestAttribute("userdata") String userDataString, @PathVariable("followingId") String followingId) {
        logger.info("Unfollow request made with followingId: " + followingId);

        Gson gson = new Gson();
        UserData userData = gson.fromJson(userDataString, UserData.class);

        ResponseModel<Void> responseModel = followingDal.unfollow(userData.getUserId(), followingId);

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(500).build();
    }
}
