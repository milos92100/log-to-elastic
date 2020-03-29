package com.logtoelastic.sandbox.nats;

import io.nats.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Client {

    private static final Logger logger = LogManager.getLogger(Client.class);

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException, ExecutionException {

        var options = new Options.Builder()
                .server("nats://localhost:4222")
                .connectionName("server")
                .connectionListener((connection, events) -> {
                    logger.info("status: {}, {}", connection.getStatus(), events);
                }).build();

        Connection nc = Nats.connect(options);

        Future<Message> replyFuture = nc.request("authenticate", "request-data".getBytes(StandardCharsets.UTF_8));
        Message reply = replyFuture.get(5, TimeUnit.SECONDS);

        logger.info("reply: {}", new String(reply.getData()));

    }
}
