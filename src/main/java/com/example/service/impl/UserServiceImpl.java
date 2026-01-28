package com.example.service.impl;

import com.example.model.User;
import com.example.service.AccountService;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private final Map<Integer, User> userMap;
    private final Set<String> userNames;
    private int idCounter;
    private final AccountService accountService;

    public UserServiceImpl(AccountService accountService) {
        this.accountService = accountService;
        this.userMap = new HashMap<>();
        this.userNames = new HashSet<>();
        this.idCounter = 0;
    }

    @Override
    public User createUser(String name) {
        if (userNames.contains(name)) {
            throw new IllegalArgumentException("User with the name '" + name + "' already exists!");
        }

        userNames.add(name);
        idCounter++;
        User newUser = new User(idCounter, name, new ArrayList<>());

        var newAccount = accountService.createAccount(newUser);
        newUser.getAccounts().add(newAccount);

        userMap.put(newUser.getId(), newUser);

        return newUser;
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }

}
