package com.example.operation.impl;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;


@Component
public class WithdrawAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final AccountService accountService;

    public WithdrawAccountCommand(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        // Логика выполнения команды
        System.out.println("Enter account id:");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to withdraw:");
        BigDecimal amountToWithdraw = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
        accountService.withdrawFromAccount(accountId, amountToWithdraw);
        System.out.printf("Successfully withdrawn amount=%s to accountId=%s%n",
                amountToWithdraw, accountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }

}
