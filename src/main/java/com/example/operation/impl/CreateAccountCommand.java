package com.example.operation.impl;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.AccountService;
import com.example.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public CreateAccountCommand(Scanner scanner,
                               UserService userService,
                               AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        // Логика выполнения команды
        System.out.println("Enter the user id to create an account for:");
        int userId = Integer.parseInt(scanner.nextLine());
        var user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user with id=" + userId));
        var account = accountService.createAccount(user);
        user.getAccounts().add(account);

        System.out.printf("New account created with Id: %s for user: %s%n",
                account.getId(), user.getName());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }

}
