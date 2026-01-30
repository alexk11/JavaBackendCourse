package com.example.service;

import com.example.persistence.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserService {

    User createUser(String name);

    Optional<User> findById(Long id);

    List<User> getAllUsers();
}
