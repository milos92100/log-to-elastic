package com.logtoelastic.core.serviceregistry.services.clients;


import com.logtoelastic.core.serviceregistry.services.ServiceException;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationRequest;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;

import java.util.concurrent.CompletableFuture;


public interface AuthenticationServiceClient {
    CompletableFuture<AuthenticationResult> authenticate(AuthenticationRequest credentials) throws ServiceException;

}
