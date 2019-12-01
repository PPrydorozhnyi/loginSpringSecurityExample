package com.peter.auth.service;

import com.peter.auth.model.entity.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
