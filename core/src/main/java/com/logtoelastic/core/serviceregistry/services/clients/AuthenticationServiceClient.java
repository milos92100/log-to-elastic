package com.logtoelastic.core.serviceregistry.services.clients;

import com.logtoelastic.core.serviceregistry.services.ServiceException;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationCredentials;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;

import java.util.concurrent.CompletableFuture;


/**
 * Authentication client, used to provide identity services
 */
public interface AuthenticationServiceClient {

    /**
     * Authenticates identity if te request.
     *
     * @param credentials Authentications credential
     * @return Authentication result
     * @throws ServiceException if fails to execute request
     */
    CompletableFuture<AuthenticationResult> authenticate(AuthenticationCredentials credentials) throws ServiceException;
}
