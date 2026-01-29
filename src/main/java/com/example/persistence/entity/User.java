package com.example.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Account> accounts;

    @Override
    public String toString() {
        return "User { id=" + id + ", name=" + name + ", " +
                "accounts: " + accounts + " }";
    }

}
