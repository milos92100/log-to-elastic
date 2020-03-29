package com.logtoelastic.core.serviceregistry;

import com.logtoelastic.core.serviceregistry.services.AuthenticationService;
import com.logtoelastic.core.serviceregistry.services.ServiceFactory;
import com.logtoelastic.core.serviceregistry.services.impl.AuthenticationServiceImpl;
import com.logtoelastic.core.serviceregistry.services.impl.ServiceFactoryImpl;

import java.util.Properties;

public class Registry {
    public static ServiceFactory createServiceFactory(Properties properties) throws Exception {
        return new ServiceFactoryImpl(properties);
    }
}
