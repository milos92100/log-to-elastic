package com.logtoelastic.authentication.processor;


import com.logtoelastic.authentication.dao.UserDao;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationCredentials;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;
import com.logtoelastic.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

public class AuthenticationProcessorImpl implements AuthenticationProcessor {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationProcessorImpl.class);

    @Inject
    private UserDao userDao;

    @Override
    public AuthenticationResult authenticate(AuthenticationCredentials credentials) {

        Optional<User> user = userDao.getUserByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());

        LOGGER.info("t-id: {}; onAuthenticate: request: {}", Thread.currentThread().getId(), credentials);

        return new AuthenticationResult(
                user.get(),
                UUID.randomUUID().toString(),
                new String[]{"USER"}
        );
    }
}
