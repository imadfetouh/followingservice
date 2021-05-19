package com.imadelfetouh.followingservice.rabbit;

import java.util.logging.Logger;

public class RabbitNonStopConsumer extends ChannelHelper {

    private static final Logger logger = Logger.getLogger(RabbitNonStopConsumer.class.getName());

    public void consume(NonStopConsumer consumer) {
        try {
            consumer.consume(getChannel());
        }
        catch (Exception e){
            logger.severe(e.getMessage());
        }
        finally {
            closeChannel();
        }
    }
}
