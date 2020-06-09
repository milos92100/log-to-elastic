package com.logtoelastic.core.serviceregistry.services.providers;

import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationCredentials;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;

import java.util.concurrent.Executor;
import java.util.function.Function;

/**
 * This service provider handles all authentication requirements.
 *
 * @author Milos Stojanovic
 */
public interface AuthenticationServiceProvider {

    /**
     * Method is called when a authentication request is received.
     * Handler function is executed in a thread provided by CommonForkJoin pool.
     *
     * @param handler function for handling the request
     */
    void onAuthenticate(Function<AuthenticationCredentials, AuthenticationResult> handler);

    /**
     * Method is called when a authentication request is received.
     * Every execution will be done in a new thread.
     *
     * @param handler  function for handling the request
     * @param executor Executor for executing handler function
     */
    void onAuthenticate(Function<AuthenticationCredentials, AuthenticationResult> handler, Executor executor);
}
