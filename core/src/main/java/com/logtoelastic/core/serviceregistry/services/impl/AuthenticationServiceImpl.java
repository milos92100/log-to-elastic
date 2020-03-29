package com.logtoelastic.core.serviceregistry.services.impl;

import com.logtoelastic.core.serviceregistry.services.AuthenticationService;
import com.logtoelastic.core.serviceregistry.services.ServiceException;
import com.logtoelastic.domain.User;
import com.logtoelastic.domain.authentication.AuthenticationCredentials;
import com.logtoelastic.domain.authentication.AuthenticationResult;
import io.nats.client.Connection;

import java.util.concurrent.CompletableFuture;

public class AuthenticationServiceImpl extends AbstractService implements AuthenticationService {
    public AuthenticationServiceImpl(Connection connection) {
        super(connection);
    }

    @Override
    public CompletableFuture<AuthenticationResult> authenticate(AuthenticationCredentials credentials) {
        return executeRequest( //
                new ServiceRequest<>("authenticate", credentials, AuthenticationResult.class) //
        );
    }
}
