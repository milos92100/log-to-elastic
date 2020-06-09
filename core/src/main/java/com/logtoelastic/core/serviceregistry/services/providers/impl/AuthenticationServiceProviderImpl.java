package com.logtoelastic.core.serviceregistry.services.providers.impl;

import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationCredentials;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;
import com.logtoelastic.core.serviceregistry.services.AbstractAuthenticationService;
import com.logtoelastic.core.serviceregistry.services.providers.AuthenticationServiceProvider;
import io.nats.client.Connection;

import java.util.concurrent.Executor;
import java.util.function.Function;

public class AuthenticationServiceProviderImpl extends AbstractAuthenticationService implements AuthenticationServiceProvider {
    public AuthenticationServiceProviderImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void onAuthenticate(Function<AuthenticationCredentials, AuthenticationResult> handler) {
        handleRequest(Subjects.AUTHENTICATE.name(), AuthenticationCredentials.class, handler);
    }

    @Override
    public void onAuthenticate(Function<AuthenticationCredentials, AuthenticationResult> handler, Executor executor) {
        handleRequest(Subjects.AUTHENTICATE.name(), AuthenticationCredentials.class, handler, executor);
    }
}
