package com.example.service;

import com.example.persistence.entity.Account;
import com.example.persistence.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface AccountService {

    List<Account> getAllAccounts();

    Account createAccount(User user);

    Optional<Account> getAccountById(Long id);

    List<Account> getUserAccounts(Long userId);

    void depositAccount(Long accountId, BigDecimal moneyToDeposit);

    void withdrawFromAccount(Long accountId, BigDecimal amountToWithdraw);

    void closeAccount(Long accountId);

    void transfer(Long fromAccountId, Long toAccountId, BigDecimal amountToTransfer);
}
