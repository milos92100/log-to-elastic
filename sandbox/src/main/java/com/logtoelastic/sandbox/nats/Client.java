package com.logtoelastic.sandbox.nats;

import com.logtoelastic.core.serviceregistry.Registry;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationCredentials;

import io.nats.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

    private static final Logger logger = LogManager.getLogger(Client.class);

    public static void main(String[] args) throws Exception {

        var options = new Options.Builder()
                .server("nats://localhost:4222")
                .connectionName("server")
                .connectionListener((connection, events) -> {
                    logger.info("status: {}, {}", connection.getStatus(), events);
                }).build();


        var factory = Registry.createServiceFactory(Nats.connect(options));
        var authClient = factory.createAuthenticationServiceClient();

        int i = 0;
        while (i < 50) {
            final int x = i;
            new Thread(() -> authClient.authenticate(new AuthenticationCredentials("test-" + x, "test-" + x))).start();
            new Thread(() -> authClient.authenticate(new AuthenticationCredentials("test-" + x, "test-" + x))).start();
            new Thread(() -> authClient.authenticate(new AuthenticationCredentials("test-" + x, "test-" + x))).start();
            new Thread(() -> authClient.authenticate(new AuthenticationCredentials("test-" + x, "test-" + x))).start();
            i++;
        }

    }
}
