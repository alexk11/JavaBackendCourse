package com.example.service;

import com.example.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(String name);
    Optional<User> findById(int id);
    List<User> getAllUsers();
}
