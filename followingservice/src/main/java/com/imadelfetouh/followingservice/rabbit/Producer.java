package com.imadelfetouh.followingservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Producer {

    void produce(Channel channel);
}
