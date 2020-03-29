package com.logtoelastic.core.serviceregistry.services;

import com.logtoelastic.domain.authentication.AuthenticationCredentials;
import com.logtoelastic.domain.authentication.AuthenticationResult;

import java.util.concurrent.CompletableFuture;

public interface AuthenticationService {
    CompletableFuture<AuthenticationResult> authenticate(AuthenticationCredentials credentials) throws ServiceException;

}
