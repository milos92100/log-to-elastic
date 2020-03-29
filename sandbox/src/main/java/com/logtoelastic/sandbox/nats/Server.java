package com.logtoelastic.sandbox.nats;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Server {

    private static final Logger logger = LogManager.getLogger(Server.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        var options = new Options.Builder()
                .server("nats://localhost:4222")
                .connectionName("server")
                .connectionListener((connection, events) -> {
                    logger.info("status: {}, {}", connection.getStatus(), events);
                }).build();

        Connection nc = Nats.connect(options);

        nc.createDispatcher((msg) -> {
            String request = new String(msg.getData(), StandardCharsets.UTF_8);
            logger.info("request: {}", request);
            request = request + " + request-data";
            Thread.sleep(2000);
            nc.publish(msg.getReplyTo(), request.getBytes(StandardCharsets.UTF_8));

        }).subscribe("authenticate");


    }
}
