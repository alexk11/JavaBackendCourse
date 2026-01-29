package com.example.operation.impl;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.persistence.entity.User;
import com.example.service.AccountService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Scanner;


@Component
@RequiredArgsConstructor
public class CreateUserCommand implements OperationCommand {

    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    @Override
    public void execute() {
        System.out.println("Enter the name for new user:");
        String name = scanner.nextLine();

        User user = userService.createUser(name);
        accountService.createAccount(user);

        System.out.printf("User '%s' created %n", name);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }

}
