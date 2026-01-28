package com.example.operation.impl;

import com.example.model.Account;
import com.example.model.User;
import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.AccountService;
import com.example.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class CloseAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public CloseAccountCommand(Scanner scanner,
                               UserService userService,
                               AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        System.out.println("Enter account id to close:");
        int accountId = Integer.parseInt(scanner.nextLine());
        Account account = accountService.closeAccount(accountId);

        User user = userService.findById(account.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s"
                        .formatted(account.getUserId())));
        user.getAccounts().remove(account);

        System.out.println("Account successfully closed with id=%s" + accountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }

}
