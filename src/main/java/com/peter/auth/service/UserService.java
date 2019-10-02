package com.peter.auth.service;

import com.peter.auth.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
