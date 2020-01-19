package com.logtoelastic.core.serviceregistry.services;

import com.logtoelastic.domain.authentication.AuthenticationCredentials;
import com.logtoelastic.domain.authentication.AuthenticationResult;

public interface AuthenticationService {
    AuthenticationResult authenticate(AuthenticationCredentials credentials);
}
