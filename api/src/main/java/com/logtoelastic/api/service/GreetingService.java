package com.logtoelastic.api.service;

import com.logtoelastic.api.dto.Greeting;
import com.logtoelastic.core.serviceregistry.services.clients.AuthenticationServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    private static final Logger logger = LogManager.getLogger(GreetingService.class);

    @Autowired
    BeanFactory beanFactory;

    public Greeting createGreeting(Long cnt, String name) {

        AuthenticationServiceClient authenticationServiceClient = (AuthenticationServiceClient) beanFactory.getBean("core.authenticationService");
        var result = authenticationServiceClient.authenticate(null);
        logger.info("createGreeting: {} {}", cnt, name);
        return new Greeting(cnt, name);
    }
}
