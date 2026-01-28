package com.example.operation;

public interface OperationCommand {

    void execute();

    ConsoleOperationType getOperationType();
}
