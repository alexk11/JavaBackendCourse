package com.example.operation.impl.display;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.persistence.entity.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ShowAllUsersCommand implements OperationCommand {

    private final UserService userService;

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
