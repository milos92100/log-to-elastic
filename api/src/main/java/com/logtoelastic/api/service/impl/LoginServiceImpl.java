package com.logtoelastic.api.service.impl;


import com.logtoelastic.api.service.LoginService;
import com.logtoelastic.core.serviceregistry.services.AuthenticationService;
import com.logtoelastic.domain.authentication.AuthenticationCredentials;
import com.logtoelastic.domain.authentication.AuthenticationResult;
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
    AuthenticationService authenticationService;

    @Override
    public AuthenticationResult login(AuthenticationCredentials credentials) throws Exception {
        var result = authenticationService.authenticate(credentials);
        return result.get();
    }
}
