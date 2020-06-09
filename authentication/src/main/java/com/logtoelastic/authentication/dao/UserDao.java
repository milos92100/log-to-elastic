package com.logtoelastic.authentication.dao;

import com.logtoelastic.domain.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> getUserByUsernameAndPassword(String username, String password);
}
