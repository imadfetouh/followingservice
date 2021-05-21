package com.imadelfetouh.followingservice.rabbit.delivercallback;

import com.google.gson.Gson;
import com.imadelfetouh.followingservice.dal.configuration.Executer;
import com.imadelfetouh.followingservice.dal.configuration.SessionType;
import com.imadelfetouh.followingservice.dal.queryexecuter.AddUserExecuter;
import com.imadelfetouh.followingservice.model.dto.UserDTO;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class AddUserDeliverCallback implements DeliverCallback {

    private final static Logger logger = Logger.getLogger(AddUserDeliverCallback.class.getName());

    private Gson gson;

    public AddUserDeliverCallback() {
        gson = new Gson();
    }

    @Override
    public void handle(String s, Delivery delivery) {
        try {
            logger.info("Message received add user");
            String json = new String(delivery.getBody(), StandardCharsets.UTF_8);
            UserDTO userDTO = gson.fromJson(json, UserDTO.class);

            Executer<Void> executer = new Executer<>(SessionType.WRITE);
            executer.execute(new AddUserExecuter(userDTO));
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
