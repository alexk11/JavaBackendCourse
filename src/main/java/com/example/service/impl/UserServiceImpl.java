package com.example.service.impl;

import com.example.persistence.entity.User;
import com.example.service.TransactionService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SessionFactory sessionFactory;
    private final TransactionService transactionService;

    @Override
    public User createUser(String name) {
        User newUser = User.builder()
                .name(name)
                .accounts(new ArrayList<>())
                .build();
        transactionService.executeInTransaction(
                session -> session.persist(newUser));
        return newUser;
    }

    @Override
    public User findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("SELECT u FROM User u", User.class)
                    .list().stream()
                    .toList();
        }
    }

}
