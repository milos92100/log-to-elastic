package com.logtoelastic.core.serviceregistry.services.impl;

import com.logtoelastic.core.serviceregistry.services.AuthenticationService;
import com.logtoelastic.domain.User;
import com.logtoelastic.domain.authentication.AuthenticationCredentials;
import com.logtoelastic.domain.authentication.AuthenticationResult;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public AuthenticationResult authenticate(AuthenticationCredentials credentials) {
        return new AuthenticationResult(new User("", "", "", ""), "", new String[]{});
    }
}
