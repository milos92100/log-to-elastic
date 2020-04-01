package com.logtoelastic.core.serviceregistry.services.clients.impl;

import com.logtoelastic.core.serviceregistry.services.AbstractAuthenticationService;
import com.logtoelastic.core.serviceregistry.services.clients.AuthenticationServiceClient;
import com.logtoelastic.core.serviceregistry.services.ServiceRequest;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationRequest;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;
import io.nats.client.Connection;

import java.util.concurrent.CompletableFuture;


public class AuthenticationServiceClientImpl extends AbstractAuthenticationService implements AuthenticationServiceClient {

    public AuthenticationServiceClientImpl(Connection connection) {
        super(connection);
    }

    @Override
    public CompletableFuture<AuthenticationResult> authenticate(AuthenticationRequest request) {
        return executeRequest( //
                new ServiceRequest<>(Subjects.AUTHENTICATE.name(), request, AuthenticationResult.class) //
        );
    }
}
