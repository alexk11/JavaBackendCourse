package com.example.operation.impl;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import com.example.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Scanner;


@Component
@RequiredArgsConstructor
public class TransferAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final AccountService accountService;

    @Override
    public void execute() {
        try {
            System.out.println("Enter source account id:");
            int fromAccountId = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter destination account id:");
            int toAccountId = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter amount to transfer:");
            BigDecimal amountToTransfer = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
            accountService.transfer(fromAccountId, toAccountId, amountToTransfer);
            System.out.printf("Successfully transferred %s from accountId = %s to accountId = %s%n",
                    amountToTransfer, fromAccountId, toAccountId);
        } catch (IllegalArgumentException e) {
            System.out.printf("Error occurred: %s", e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
