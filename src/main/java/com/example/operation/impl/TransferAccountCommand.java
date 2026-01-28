package com.example.operation.impl;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;


@Component
public class TransferAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final AccountService accountService;

    public TransferAccountCommand(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        // Логика выполнения команды
        System.out.println("Enter source account id:");
        int fromAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter destination account id:");
        int toAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to transfer:");
        BigDecimal amountToTransfer = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
        accountService.transfer(fromAccountId, toAccountId, amountToTransfer);
        System.out.printf("Successfully transferred %s from accountId %s to accountId %s%n",
                amountToTransfer, fromAccountId, toAccountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
