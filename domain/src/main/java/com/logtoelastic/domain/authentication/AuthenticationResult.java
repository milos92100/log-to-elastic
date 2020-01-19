package com.logtoelastic.domain.authentication;

import com.logtoelastic.domain.User;

public class AuthenticationResult {
    private User identity;
    private String accessToken;
    private String[] roles;

    public AuthenticationResult(User identity, String accessToken, String[] roles) {
        this.identity = identity;
        this.accessToken = accessToken;
        this.roles = roles;
    }

    public User getIdentity() {
        return identity;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String[] getRoles() {
        return roles;
    }
}
