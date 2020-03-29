package com.logtoelastic.api.config;

import com.logtoelastic.core.serviceregistry.services.AuthenticationService;
import com.logtoelastic.core.serviceregistry.services.ServiceFactory;
import org.elasticsearch.common.inject.Inject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ServiceProviderConfiguration {

    @Autowired
    BeanFactory beanFactory;

    @Bean
    @Lazy
    AuthenticationService authenticationService() {
        return beanFactory.getBean("core.serviceFactory", ServiceFactory.class).createAuthenticationService();
    }
}
