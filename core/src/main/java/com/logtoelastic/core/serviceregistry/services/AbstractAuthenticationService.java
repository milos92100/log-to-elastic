package com.logtoelastic.core.serviceregistry.services;

import io.nats.client.Connection;

public abstract class AbstractAuthenticationService extends AbstractService {
    protected enum Subjects {
        AUTHENTICATE;
    }

    public AbstractAuthenticationService(Connection connection) {
        super(connection);
    }
}
