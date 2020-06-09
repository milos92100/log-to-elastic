package com.logtoelastic.authentication.processor;

import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationCredentials;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;

public interface AuthenticationProcessor {

    AuthenticationResult authenticate(AuthenticationCredentials credentials);
}
