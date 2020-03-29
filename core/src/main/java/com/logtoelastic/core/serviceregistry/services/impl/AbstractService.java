package com.logtoelastic.core.serviceregistry.services.impl;

import com.google.gson.Gson;
import com.logtoelastic.core.serviceregistry.services.ServiceException;
import io.nats.client.Connection;
import io.nats.client.Message;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.*;

public class AbstractService {
    private Connection connection;
    private Gson gson;
    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    public AbstractService(Connection connection) {
        this.connection = connection;
        this.gson = new Gson();
    }

    protected <A, R> CompletableFuture<R> executeRequest(ServiceRequest<A, R> request) {
        var jsonRequest = gson.toJson(request.getArgument());
        return CompletableFuture.supplyAsync(() -> {
            try {
                checkConnectionAndAwait();

                Message message = connection.request( //
                        request.getSubject(), //
                        jsonRequest.getBytes(StandardCharsets.UTF_8), //
                        Duration.ofSeconds(30) //
                );
                return gson.fromJson(new String(message.getData()), request.getReturnValueClass());
            } catch (Exception e) {
                throw new ServiceException(e.getMessage(), e.getCause());
            }
        }, executorService);
    }

    private void checkConnectionAndAwait() {
        if (connection.getStatus() == Connection.Status.CLOSED) {
            throw new ServiceException("Service is not available", null);
        }

        long now = System.currentTimeMillis();
        while (now - System.currentTimeMillis() < 500 && !Thread.currentThread().isInterrupted()) {
            if (connection.getStatus() == Connection.Status.CONNECTED) {
                return;
            }
        }

        if (connection.getStatus() != Connection.Status.CONNECTED) {
            throw new ServiceException("Service is not available", null);
        }
    }
}
