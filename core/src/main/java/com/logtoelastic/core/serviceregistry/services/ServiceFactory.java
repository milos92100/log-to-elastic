package com.logtoelastic.core.serviceregistry.services;

import com.logtoelastic.core.serviceregistry.services.clients.AuthenticationServiceClient;
import com.logtoelastic.core.serviceregistry.services.providers.AuthenticationServiceProvider;

public interface ServiceFactory {
    AuthenticationServiceClient createAuthenticationServiceClient();
    AuthenticationServiceProvider createAuthenticationServiceProvider();
}
