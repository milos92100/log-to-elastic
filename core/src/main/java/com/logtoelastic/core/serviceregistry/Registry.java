package com.logtoelastic.core.serviceregistry;

import com.logtoelastic.core.serviceregistry.services.ServiceFactory;
import com.logtoelastic.core.serviceregistry.services.ServiceFactoryImpl;
import io.nats.client.Connection;

import java.util.Properties;

public class Registry {
    public static ServiceFactory createServiceFactory(Properties properties) throws Exception {
        return new ServiceFactoryImpl(properties);
    }

    public static ServiceFactory createServiceFactory(Connection connection) throws Exception {
        return new ServiceFactoryImpl(connection);
    }

}
