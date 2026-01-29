package com.example.operation.impl;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.persistence.entity.Account;
import com.example.service.AccountService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Scanner;


@Component
@RequiredArgsConstructor
public class CreateAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    @Override
    public void execute() {
        try {
            System.out.println("Enter the user id to create an account for:");
            int userId = Integer.parseInt(scanner.nextLine());
            var user = userService.findById(userId);
            if (user == null) {
                throw new IllegalArgumentException(
                        "User with id=%s does not exist".formatted(userId));
            }
            Account account = accountService.createAccount(user);

            System.out.printf("New account created with id: %s for user: %s%n",
                    account.getId(), user.getName());

        } catch (IllegalArgumentException e) {
            System.out.printf("Error occurred: %s", e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }

}
