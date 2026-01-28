package com.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;


@Getter
@Setter
@Builder
public class AccountDto {

    private final long id;
    private final long userId;
    private BigDecimal moneyAmount;

    public AccountDto(long id, long userId, BigDecimal moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account {" +
                " id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount + " }";
    }

}
