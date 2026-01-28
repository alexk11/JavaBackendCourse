package com.example.operation.impl;

import com.example.model.User;
import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class CreateUserCommand implements OperationCommand {

    private final Scanner scanner;
    private final UserService userService;

    public CreateUserCommand(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    @Override
    public void execute() {
        // Логика выполнения команды
        System.out.println("Enter the name for new user:");
        String name = scanner.nextLine();
        User user = userService.createUser(name);
        System.out.println("User created: " + user.toString());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }

}
