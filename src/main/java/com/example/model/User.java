package com.example.model;

import lombok.Getter;

import java.util.List;

@Getter
public class User {

    private final int id;
    private final String name;
    private final List<Account> accounts;

    public User(int id, String name, List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "User { id=" + id + ", name='" + name + ", accounts=" + accounts + " }";
    }

}
