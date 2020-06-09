package com.logtoelastic.authentication.dao.impl;

import com.logtoelastic.authentication.dao.UserDao;
import com.logtoelastic.domain.User;

import java.util.Optional;
import java.util.UUID;

public class UserDaoImpl implements UserDao {

    @Override
    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        var user = new User(UUID.randomUUID().toString(), "foo", "Foo", "Foo");
        return Optional.of(user);
    }
}
