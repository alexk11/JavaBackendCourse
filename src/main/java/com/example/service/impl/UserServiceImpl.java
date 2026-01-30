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
        return transactionService.executeInTransactionGeneric(() -> {
            var session = sessionFactory.getCurrentSession();

            var existedUser = session.createQuery("FROM User WHERE name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResultOrNull();
            if (existedUser != null) {
                throw new IllegalArgumentException("User with the name=%s already exists"
                        .formatted(name));
            }

            User user = new User(null, name, new ArrayList<>());
            session.persist(user);

            return user;
        });
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.of(session.get(User.class, id));
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("SELECT u FROM User u", User.class)
                    .list();
        }
    }

}
