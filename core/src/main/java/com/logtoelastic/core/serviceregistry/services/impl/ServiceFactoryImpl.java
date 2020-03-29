package com.logtoelastic.core.serviceregistry.services.impl;

import com.logtoelastic.core.serviceregistry.services.AuthenticationService;
import com.logtoelastic.core.serviceregistry.services.ServiceFactory;
import io.nats.client.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ServiceFactoryImpl extends AbstractServiceFactory {

    private static final Logger logger = LogManager.getLogger(ServiceFactoryImpl.class);

    public ServiceFactoryImpl(Properties properties) throws Exception {
        super(properties);
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
    public AuthenticationService createAuthenticationService() {
        return createService(AuthenticationServiceImpl.class);
    }
}
