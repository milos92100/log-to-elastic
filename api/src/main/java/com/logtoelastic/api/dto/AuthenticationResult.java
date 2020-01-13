package com.logtoelastic.api.dto;

public class AuthenticationResult {
    private String accessToken;
    private String[] roles;

    public AuthenticationResult(String accessToken, String[] roles) {
        this.accessToken = accessToken;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String[] getRoles() {
        return roles;
    }
}
