package com.logtoelastic.api.dto;

public class AuthenticationCredentials {
    private String username;
    private String password;

    public AuthenticationCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
