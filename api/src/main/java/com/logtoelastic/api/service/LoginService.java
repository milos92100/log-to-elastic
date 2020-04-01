package com.logtoelastic.api.service;

import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationRequest;
import com.logtoelastic.core.serviceregistry.dto.auhentication.AuthenticationResult;

public interface LoginService {
    AuthenticationResult login(AuthenticationRequest credentials) throws Exception;
}
