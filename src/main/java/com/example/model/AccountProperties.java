package com.example.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Getter
@PropertySource("classpath:application.properties")
public class AccountProperties {

    private final BigDecimal defaultAmount;
    private final BigDecimal transferCommission;

    public AccountProperties(@Value("${account.default-amount}") BigDecimal
                                     defaultAmount,
                             @Value("${account.transfer-commission}") BigDecimal
                                     transferCommission) {
          this.defaultAmount = defaultAmount;
          this.transferCommission = transferCommission;
    }

}
