package com.example.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "money_amount")
    private BigDecimal moneyAmount;

    // This is the foreign key column in the USERS table
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Account { id=" + id + ", userId=" + user.getId() +
                ", moneyAmount " + moneyAmount + " }";
    }

}
