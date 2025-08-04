package com.iut.user.service;

import com.iut.Repository;
import com.iut.user.model.User;
import com.iut.user.repo.UserRepository;

public class UserService {
    private final Repository<User, String> repository;

    public UserService(UserRepository userRepository) {
    this.repository = userRepository;
}


    public boolean createUser(User user) {
        if (repository.existsById(user.getId())) {
            return false;
        }
        return repository.save(user);
    }

    // دریافت کاربر بر اساس ID
    public User getUser(String userId) {
        return repository.findById(userId);
    }

    // بررسی وجود کاربر
    public boolean existsById(String userId) {
        return repository.existsById(userId);
    }
}
