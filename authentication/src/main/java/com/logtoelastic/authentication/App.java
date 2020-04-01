package com.logtoelastic.authentication;

import com.logtoelastic.core.serviceregistry.Registry;
import com.logtoelastic.core.serviceregistry.services.ServiceFactory;
import com.logtoelastic.core.serviceregistry.services.providers.AuthenticationServiceProvider;
import com.logtoelastic.domain.User;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        ServiceFactory serviceFactory = null;

        try {
            InputStream propertiesStream = Objects.requireNonNull( //
                    App.class.getClassLoader().getResourceAsStream("app.properties") //
            );

            Properties properties = new Properties();
            properties.load(propertiesStream);

            var connectionString = properties.getProperty("natsConnectionString");

            var options = new Options.Builder()
                    .server(connectionString)
                    .connectionListener((conn, events) -> {
                        logger.info("status: {}, {}", conn.getStatus(), events);
                    }).build();


            serviceFactory = Registry.createServiceFactory(Nats.connect(options));
            logger.info("Authentication started...");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }

        AuthenticationServiceProvider serviceProvider = serviceFactory.createAuthenticationServiceProvider();

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        serviceProvider.onAuthenticate(authenticationRequest -> {
            logger.info("t-id: {}; onAuthenticate: request: {}", Thread.currentThread().getId(), authenticationRequest);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new AuthenticationResult(
                    new User(UUID.randomUUID().toString(), "foo", "Foo", "Bar"),
                    UUID.randomUUID().toString(),
                    new String[]{"USER"}
            );
        }, executorService);

    }
}
