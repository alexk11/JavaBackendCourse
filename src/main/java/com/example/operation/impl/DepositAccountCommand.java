package com.example.operation.impl;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;


@Component
public class DepositAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final AccountService accountService;

    public DepositAccountCommand(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        System.out.println("Enter account id:");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to deposit:");
        BigDecimal amountToDeposit = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
        accountService.depositAccount(accountId, amountToDeposit);
        System.out.printf("Successfully deposited amount=%s to accountId=%s%n",
                amountToDeposit, accountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}