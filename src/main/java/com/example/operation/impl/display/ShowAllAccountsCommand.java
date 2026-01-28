package com.example.operation.impl.display;

import com.example.model.Account;
import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ShowAllAccountsCommand implements OperationCommand {

    private final AccountService accountService;

    public ShowAllAccountsCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        List<Account> accounts = accountService.getAllAccounts();
        if(accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.println("List of all accounts:");
        accounts.forEach(System.out::println);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_ACCOUNTS;
    }

}
