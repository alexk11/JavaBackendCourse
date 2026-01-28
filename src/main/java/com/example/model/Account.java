package com.example.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Account {

    private final int id;
    private final int userId;
    private final String userName;
    private BigDecimal moneyAmount;

    public Account(int id, int userId, String userName, BigDecimal moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account {" +
                " id=" + id +
                ", userId=" + userId +
                ", userName=" + userName +
                ", moneyAmount=" + moneyAmount + " }";
    }

}
