package com.logtoelastic.api.service;

import com.logtoelastic.domain.authentication.AuthenticationCredentials;
import com.logtoelastic.domain.authentication.AuthenticationResult;

public interface LoginService {
    AuthenticationResult login(AuthenticationCredentials credentials) throws Exception;
}
