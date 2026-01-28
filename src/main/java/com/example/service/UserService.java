package com.example.service;

import com.example.persistence.entity.User;
import java.util.List;


public interface UserService {

    User createUser(String name);

    User findById(long id);

    List<User> getAllUsers();
}
