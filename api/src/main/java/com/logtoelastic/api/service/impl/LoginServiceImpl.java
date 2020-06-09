package com.logtoelastic.api.service.impl;


import com.logtoelastic.api.service.LoginService;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationCredentials;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;
import com.logtoelastic.core.serviceregistry.services.clients.AuthenticationServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class LoginServiceImpl implements LoginService {

    @Autowired
    @Lazy
    AuthenticationServiceClient authenticationServiceClient;

    @Override
    public AuthenticationResult login(AuthenticationCredentials credentials) throws Exception {
        var result = authenticationServiceClient.authenticate(credentials);
        return result.get();
    }
}
