package com.logtoelastic.api.service.impl;


import com.logtoelastic.api.service.LoginService;
import com.logtoelastic.core.serviceregistry.services.AuthenticationService;
import com.logtoelastic.domain.authentication.AuthenticationCredentials;
import com.logtoelastic.domain.authentication.AuthenticationResult;
import org.elasticsearch.common.inject.Inject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginServiceImpl implements LoginService {
    @Inject
    BeanFactory beanFactory;

    @Override
    public AuthenticationResult login(AuthenticationCredentials credentials) {
        AuthenticationService authenticationService = (AuthenticationService) beanFactory.getBean("core.authenticationService");
        return null;
    }
}
