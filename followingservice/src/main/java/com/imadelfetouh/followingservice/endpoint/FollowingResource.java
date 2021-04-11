package com.imadelfetouh.followingservice.endpoint;

import com.imadelfetouh.followingservice.dalinterface.FollowingDal;
import com.imadelfetouh.followingservice.model.dto.FollowingDTO;
import com.imadelfetouh.followingservice.model.response.ResponseModel;
import com.imadelfetouh.followingservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("following")
public class FollowingResource {

    @Autowired
    private FollowingDal followingDal;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FollowingDTO>> getFollowingUsers() {
        ResponseModel<List<FollowingDTO>> responseModel = followingDal.getFollowingUsers("123");

        if(responseModel.getResponseType().equals(ResponseType.EMPTY)) {
            return ResponseEntity.noContent().build();
        }
        else if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().body(responseModel.getData());
        }

        return ResponseEntity.status(500).build();
    }

    @PostMapping()
    public ResponseEntity<List<FollowingDTO>> addFollowing() {
        ResponseModel<Void> responseModel = followingDal.addFollowing("123", "12345");

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(500).build();
    }

    @PutMapping()
    public ResponseEntity<List<FollowingDTO>> unfollow() {
        ResponseModel<Void> responseModel = followingDal.unfollow("123", "1234");

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(500).build();
    }
}
