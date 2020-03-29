package com.logtoelastic.core.serviceregistry.services.impl;

import com.logtoelastic.core.serviceregistry.services.ServiceFactory;
import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public abstract class AbstractServiceFactory implements ServiceFactory {

    private Connection connection;

    public AbstractServiceFactory(Properties properties) throws Exception {
        if (properties == null) {
            throw new IllegalArgumentException("properties must not be null");
        }

        String connectionString = properties.getProperty("natsConnectionString");

        if (connectionString == null) {
            throw new IllegalArgumentException("property 'natsConnectionString' must not be null");
        }

        var options = new Options.Builder()
                .server(connectionString)
                .connectionName("client-" + UUID.randomUUID())
                .build();

        connection = Nats.connect(options);
    }

    public Connection getConnection() {
        return connection;
    }
}
