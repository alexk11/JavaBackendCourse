package com.example.service;

import com.example.model.Account;
import com.example.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface AccountService {

    List<Account> getAllAccounts();

    Account createAccount(User user);

    Optional<Account> findAccountById(int id);

    List<Account> getUserAccounts(int userId);

    void depositAccount(int accountId, BigDecimal moneyToDeposit);

    void withdrawFromAccount(int accountId, BigDecimal amountToWithdraw);

    Account closeAccount(int accountId);

    void transfer(int fromAccountId, int toAccountId, BigDecimal amountToTransfer);
}
