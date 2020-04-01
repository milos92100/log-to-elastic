package com.logtoelastic.api.config;

import com.logtoelastic.core.serviceregistry.services.clients.AuthenticationServiceClient;
import com.logtoelastic.core.serviceregistry.services.ServiceFactory;
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
    AuthenticationServiceClient authenticationService() {
        return beanFactory.getBean("core.serviceFactory", ServiceFactory.class).createAuthenticationServiceClient();
    }
}
