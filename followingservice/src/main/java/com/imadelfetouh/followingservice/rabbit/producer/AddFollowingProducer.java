package com.imadelfetouh.followingservice.rabbit.producer;

import com.google.gson.Gson;
import com.imadelfetouh.followingservice.model.dto.NewFollowingDTO;
import com.imadelfetouh.followingservice.rabbit.Producer;
import com.rabbitmq.client.Channel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddFollowingProducer implements Producer {

    private static final Logger logger = Logger.getLogger(AddFollowingProducer.class.getName());

    private final NewFollowingDTO newFollowingDTO;
    private final String exchange_name;
    private final Gson gson;

    public AddFollowingProducer(NewFollowingDTO newFollowingDTO) {
        this.newFollowingDTO = newFollowingDTO;
        this.exchange_name = "addfollowingexchange";
        gson = new Gson();
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(exchange_name, "direct", true);
            String json = gson.toJson(newFollowingDTO);

            channel.basicPublish(exchange_name, "", null, json.getBytes());
        }
        catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
