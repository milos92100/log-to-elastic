package com.logtoelastic.core.serviceregistry.services;

import com.logtoelastic.core.serviceregistry.services.clients.AuthenticationServiceClient;
import com.logtoelastic.core.serviceregistry.services.clients.impl.AuthenticationServiceClientImpl;
import com.logtoelastic.core.serviceregistry.services.providers.AuthenticationServiceProvider;
import com.logtoelastic.core.serviceregistry.services.providers.impl.AuthenticationServiceProviderImpl;
import io.nats.client.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.Properties;

public class ServiceFactoryImpl extends AbstractServiceFactory {

    private static final Logger logger = LogManager.getLogger(ServiceFactoryImpl.class);

    public ServiceFactoryImpl(Properties properties) throws Exception {
        super(properties);
    }

    public ServiceFactoryImpl(Connection connection) {
        super(connection);
    }

    private <S> S createService(Class<S> serviceClass) {
        try {
            Constructor<S> constructor = serviceClass.getConstructor(Connection.class);
            return constructor.newInstance(getConnection());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public AuthenticationServiceClient createAuthenticationServiceClient() {
        return createService(AuthenticationServiceClientImpl.class);
    }

    @Override
    public AuthenticationServiceProvider createAuthenticationServiceProvider() {
        return createService(AuthenticationServiceProviderImpl.class);
    }
}
