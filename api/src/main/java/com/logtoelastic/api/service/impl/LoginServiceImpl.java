package com.logtoelastic.api.service.impl;


import com.logtoelastic.api.service.LoginService;
import com.logtoelastic.core.serviceregistry.services.clients.AuthenticationServiceClient;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationRequest;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;
import org.elasticsearch.common.inject.Inject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class LoginServiceImpl implements LoginService {
    @Inject
    BeanFactory beanFactory;

    @Autowired
    @Lazy
    AuthenticationServiceClient authenticationServiceClient;

    @Override
    public AuthenticationResult login(AuthenticationRequest credentials) throws Exception {
        var result = authenticationServiceClient.authenticate(credentials);
        return result.get();
    }
}
