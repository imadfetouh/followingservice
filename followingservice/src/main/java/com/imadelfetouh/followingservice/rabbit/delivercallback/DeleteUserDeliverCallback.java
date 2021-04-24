package com.imadelfetouh.followingservice.rabbit.delivercallback;

import com.imadelfetouh.followingservice.dal.configuration.Executer;
import com.imadelfetouh.followingservice.dal.configuration.SessionType;
import com.imadelfetouh.followingservice.dal.queryexecuter.DeleteUserExecuter;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteUserDeliverCallback implements DeliverCallback {

    private static final Logger logger = Logger.getLogger(DeleteUserDeliverCallback.class.getName());

    @Override
    public void handle(String s, Delivery delivery) {
        try {
            String userId = new String(delivery.getBody(), StandardCharsets.UTF_8);

            Executer<Void> executer = new Executer<>(SessionType.WRITE);
            executer.execute(new DeleteUserExecuter(userId));
        }
        catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
