package com.logtoelastic.api.service.impl;


import com.logtoelastic.api.service.LoginService;
import com.logtoelastic.domain.authentication.AuthenticationCredentials;
import com.logtoelastic.domain.authentication.AuthenticationResult;
import org.springframework.stereotype.Component;

@Component
public class LoginServiceImpl implements LoginService {

    @Override
    public AuthenticationResult login(AuthenticationCredentials credentials) {
        return null;
    }
}
