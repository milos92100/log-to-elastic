package com.logtoelastic.api.service.impl;

import com.logtoelastic.api.dto.AuthenticationCredentials;
import com.logtoelastic.api.dto.AuthenticationResult;
import com.logtoelastic.api.service.AuthenticationService;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public AuthenticationResult authenticate(AuthenticationCredentials credentials) {
        return null;
    }
}
