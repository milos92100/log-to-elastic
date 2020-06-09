package com.logtoelastic.authentication;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.logtoelastic.authentication.processor.AuthenticationProcessor;
import com.logtoelastic.core.serviceregistry.Registry;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationCredentials;
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
    private static final Logger LOGGER = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        ServiceFactory serviceFactory = null;
        Properties properties = null;

        try {
            var propertiesStream = Objects.requireNonNull( //
                    App.class.getClassLoader().getResourceAsStream("app.properties") //
            );

            properties = new Properties();
            properties.load(propertiesStream);

            var connectionString = properties.getProperty("natsConnectionString");

            var options = new Options.Builder()
                    .server(connectionString)
                    .connectionListener((conn, events) -> {
                        LOGGER.info("status: {}, {}", conn.getStatus(), events);
                    }).build();


            serviceFactory = Registry.createServiceFactory(Nats.connect(options));
            LOGGER.info("Authentication server started...");

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(1);
        }

        var serviceProvider = serviceFactory.createAuthenticationServiceProvider();
        var executorService = Executors.newFixedThreadPool(20);

        var injector = Guice.createInjector(new Module(properties));
        var processor = injector.getInstance(AuthenticationProcessor.class);

        serviceProvider.onAuthenticate(processor::authenticate, executorService);
    }
}
