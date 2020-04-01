package com.logtoelastic.core.serviceregistry.services.providers.impl;

import com.logtoelastic.core.serviceregistry.services.AbstractAuthenticationService;
import com.logtoelastic.core.serviceregistry.services.clients.impl.AuthenticationServiceClientImpl;
import com.logtoelastic.core.serviceregistry.services.providers.AuthenticationServiceProvider;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationRequest;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;
import io.nats.client.Connection;

import java.util.concurrent.Executor;
import java.util.function.Function;

public class AuthenticationServiceProviderImpl extends AbstractAuthenticationService implements AuthenticationServiceProvider {
    public AuthenticationServiceProviderImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void onAuthenticate(Function<AuthenticationRequest, AuthenticationResult> handler) {
        handleRequest(AuthenticationServiceClientImpl.Subjects.AUTHENTICATE.name(), AuthenticationRequest.class, handler);
    }

    @Override
    public void onAuthenticate(Function<AuthenticationRequest, AuthenticationResult> handler, Executor executor) {
        handleRequest(AuthenticationServiceClientImpl.Subjects.AUTHENTICATE.name(), AuthenticationRequest.class, handler, executor);
    }
}
