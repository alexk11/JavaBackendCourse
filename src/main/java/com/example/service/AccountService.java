package com.example.service;

import com.example.persistence.entity.Account;
import com.example.persistence.entity.User;

import java.math.BigDecimal;
import java.util.List;


public interface AccountService {

    List<Account> getAllAccounts();

    Account createAccount(User user);

    Account getAccountById(long id);

    List<Account> getUserAccounts(long userId);

    void depositAccount(long accountId, BigDecimal moneyToDeposit);

    void withdrawFromAccount(long accountId, BigDecimal amountToWithdraw);

    void closeAccount(long accountId);

    void transfer(long fromAccountId, long toAccountId, BigDecimal amountToTransfer);
}
