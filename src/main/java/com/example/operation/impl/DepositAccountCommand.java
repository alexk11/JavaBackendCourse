package com.example.operation.impl;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;


@Component
@RequiredArgsConstructor
public class DepositAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final AccountService accountService;

    @Override
    public void execute() {
        try {
            System.out.println("Enter account id:");
            Long accountId = Long.parseLong(scanner.nextLine());
            System.out.println("Enter amount to deposit:");
            BigDecimal amountToDeposit = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
            accountService.depositAccount(accountId, amountToDeposit);
            System.out.printf("Successfully deposited amount=%s to accountId=%s%n",
                    amountToDeposit, accountId);
        } catch (IllegalArgumentException e) {
            System.out.printf("Error occurred: %s", e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}