package com.logtoelastic.api.service;

import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationCredentials;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;

public interface LoginService {
    AuthenticationResult login(AuthenticationCredentials credentials) throws Exception;
}
