package com.logtoelastic.api.service;

import com.logtoelastic.api.dto.AuthenticationCredentials;
import com.logtoelastic.api.dto.AuthenticationResult;

public interface AuthenticationService {
    AuthenticationResult authenticate(AuthenticationCredentials credentials);
}
