package com.example.listener;

import com.example.operation.ConsoleOperationType;
import com.example.operation.OperationCommand;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


@Component
public class OperationsConsoleListener {

    private final Map<ConsoleOperationType, OperationCommand> commandMap;
    private final Scanner scanner;

    public OperationsConsoleListener(List<OperationCommand> commands,
                                     Scanner scanner,
                                     Map<ConsoleOperationType, OperationCommand> commandMap) {
        this.scanner = scanner;
        this.commandMap = commandMap;
        commands.forEach(command ->
                commandMap.put(command.getOperationType(), command));
    }

    @PostConstruct
    public void postConstruct() {
        Thread consoleListenerThread = new Thread(this::processOperations);
        consoleListenerThread.start();
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Console listener stopped");
    }

    /**
     * Main method business logic loop.
     */
    private void processOperations() {
        System.out.println("Processing operations in thread " + Thread.currentThread().getName());
        while(!Thread.currentThread().isInterrupted()) {
            ConsoleOperationType operationType = readOperationInput();
            if (operationType != null) {
                if (operationType.compareTo(ConsoleOperationType.STOP) == 0) {
                    Thread.currentThread().interrupt();
                    System.out.println("Console listener stopped");
                } else {
                    handleOperation(operationType);
                }
            }
        }
    }

    private void printAllAvailableOperations() {
        System.out.println("Please type next operation. Type 'STOP' to exit.");
        commandMap.keySet().forEach(System.out::println);
    }

    private ConsoleOperationType readOperationInput() {
        printAllAvailableOperations();
        String nextOperation = scanner.nextLine();
        System.out.println("Next operation typed as " + nextOperation);
        try {
            return ConsoleOperationType.valueOf(nextOperation);
        } catch (IllegalArgumentException e) {
            System.out.println("No such command found");
        }
        return null;
    }

    private void handleOperation(ConsoleOperationType operation) {
        System.out.println("Handling operation " + operation.name());
        try {
            commandMap.get(operation).execute();
        } catch (Exception e) {
            System.out.printf(
                    "Error executing command %s: error=%s%n", operation,
                    e.getMessage()
            );
        }
    }

}
