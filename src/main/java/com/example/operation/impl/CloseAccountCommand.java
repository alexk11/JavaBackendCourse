package com.example.operation.impl;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Scanner;


@Component
@RequiredArgsConstructor
public class CloseAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final AccountService accountService;

    @Override
    public void execute() {
        try {
            System.out.println("Enter account id to close:");
            int accountId = Integer.parseInt(scanner.nextLine());
            accountService.closeAccount(accountId);
            System.out.printf("Account with id=%s successfully closed", accountId);
        } catch (IllegalArgumentException e) {
            System.out.printf("Error occurred: %s", e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }

}
