package com.example.operation.impl.display;

import com.example.model.User;
import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ShowAllUsersCommand implements OperationCommand {

    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("List of all users:");
        users.forEach(System.out::println);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }

}
