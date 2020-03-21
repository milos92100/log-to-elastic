package com.logtoelastic.api.service;

import com.logtoelastic.api.dto.Greeting;
import com.logtoelastic.core.serviceregistry.services.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.inject.Inject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    private static final Logger logger = LogManager.getLogger(GreetingService.class);

    @Autowired
    BeanFactory beanFactory;

    public Greeting createGreeting(Long cnt, String name) {

        AuthenticationService authenticationService = (AuthenticationService) beanFactory.getBean("core.authenticationService");
        var result = authenticationService.authenticate(null);
        logger.info("createGreeting: {} {}", cnt, name);
        return new Greeting(cnt, name);
    }
}
