package com.logtoelastic.core.serviceregistry.dto.auhentication;

/**
 * This class represents a container which holds the input which is required
 * for the authentication fon an user
 */
public final class AuthenticationCredentials {
    private final String username;
    private final String password;

    /**
     * Constructor
     *
     * @param username Username to use in authentication
     * @param password Password to use in authentication
     */
    public AuthenticationCredentials(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username must not be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password must not be empty");
        }

        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username of the credentials
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the credentials
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

}
