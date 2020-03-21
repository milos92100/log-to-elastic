package com.logtoelastic.core.serviceregistry;

import com.logtoelastic.core.serviceregistry.services.AuthenticationService;
import com.logtoelastic.core.serviceregistry.services.impl.AuthenticationServiceImpl;
import com.rabbitmq.client.Connection;

import java.util.Map;
import java.util.Properties;

public class Registry {

    private static Map<String, String> SERVICE_QUEUE_DEFINITIONS = Map.of(
            AuthenticationService.class.getName(), "authenticate_queue"
    );

    public static AuthenticationService createAuthenticationService(Connection connection) {
        return new AuthenticationServiceImpl();
    }

}
