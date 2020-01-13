package com.logtoelastic.sandbox.rabbitmq;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer {
    private static final Logger logger = LogManager.getLogger(Consumer.class);

    private static final String EXCHANGE_NAME = "create-account-command";

    public static void main(String[] args) {
        try {

            String serviceName = "";
            if (args.length > 0) {
                serviceName = args[0];
            }

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("admin");
            factory.setPassword("admin");
            factory.setConnectionTimeout(60000);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String queueName = channel.queueDeclare(EXCHANGE_NAME + ":" + serviceName, true, false, false, null).getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });

        } catch (Exception e) {
            logger.error(e);
        }
    }
}
