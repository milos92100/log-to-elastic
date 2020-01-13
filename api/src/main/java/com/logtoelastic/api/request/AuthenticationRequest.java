package com.logtoelastic.api.request;

import com.logtoelastic.api.request.validation.annotation.NotNullOrEmpty;

public class AuthenticationRequest {
    @NotNullOrEmpty(message = "Username not provided")
    public String username;

    @NotNullOrEmpty(message = "Password not provided")
    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
