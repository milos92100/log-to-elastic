package com.logtoelastic.authentication;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.logtoelastic.authentication.dao.UserDao;
import com.logtoelastic.authentication.dao.impl.UserDaoImpl;
import com.logtoelastic.authentication.processor.AuthenticationProcessor;
import com.logtoelastic.authentication.processor.AuthenticationProcessorImpl;

import java.util.Properties;

public class Module extends AbstractModule {

    private Properties properties;

    public Module(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        bind(AuthenticationProcessor.class).to(AuthenticationProcessorImpl.class).asEagerSingleton();
        bind(UserDao.class).to(UserDaoImpl.class);
    }

    @Provides
    Properties provideProperties() {
        return properties;
    }

}
