package com.example.service.impl;

import com.example.model.Account;
import com.example.model.AccountProperties;
import com.example.model.User;
import com.example.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class AccountServiceImpl implements AccountService {

    private final Map<Integer, Account> acccountMap;
    private int idCounter;
    private final AccountProperties accountProperties;

    public AccountServiceImpl(AccountProperties accountProperties) {
        this.acccountMap = new HashMap<>();
        this.idCounter = 0;
        this.accountProperties = accountProperties;
    }

    @Override
    public List<Account> getAllAccounts() {
        return acccountMap.values()
                .stream()
                .toList();
    }

    @Override
    public Account createAccount(User user) {
        idCounter++;
        Account account = new Account(idCounter,
                user.getId(),
                user.getName(),
                accountProperties.getDefaultAmount());
        acccountMap.put(account.getId(), account);
        return account;
    }

    @Override
    public Optional<Account> findAccountById(int id) {
        return Optional.ofNullable(acccountMap.get(id));
    }

    @Override
    public List<Account> getUserAccounts(int userId) {
        return acccountMap.values()
                .stream()
                .filter(account -> account.getUserId() == userId)
                .toList();
    }

    @Override
    public void depositAccount(int accountId, BigDecimal moneyToDeposit) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=" + accountId));
        if (moneyToDeposit.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Cannot deposit not positive amount: amount=" + moneyToDeposit);
        }
        account.setMoneyAmount(account.getMoneyAmount().add(moneyToDeposit));
    }

    @Override
    public void withdrawFromAccount(int accountId, BigDecimal amountToWithdraw) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=" + accountId));

        if (amountToWithdraw.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cannot withdraw not positive amount: amount=" + amountToWithdraw);
        }
        if (account.getMoneyAmount().compareTo(amountToWithdraw) < 0) {
            throw new IllegalArgumentException(
                    "Cannot withdraw from account: id=%s, moneyAmount=%s, attemptedWithdraw=%s"
                            .formatted(accountId, account.getMoneyAmount(), amountToWithdraw)
            );
        }
        account.setMoneyAmount(account.getMoneyAmount().subtract(amountToWithdraw));
    }

    @Override
    public Account closeAccount(int accountId) {
        var accountToRemove = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=" + accountId));
        List<Account> accountList = getUserAccounts(accountToRemove.getUserId());
        if (accountList.size() == 1) {
            throw new IllegalArgumentException("Cannot close the only one user's account");
        }
        Account accountToDeposit = accountList.stream()
                .filter(it -> it.getId() != accountId)
                .findFirst()
                .orElseThrow();

        accountToDeposit.setMoneyAmount(
                accountToDeposit.getMoneyAmount().add(accountToRemove.getMoneyAmount()));

        acccountMap.remove(accountId);
        return accountToRemove;
    }

    @Override
    public void transfer(int fromAccountId, int toAccountId, BigDecimal amountToTransfer) {
        var accountFrom = findAccountById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=" + fromAccountId));
        var accountTo = findAccountById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=" + toAccountId));
        if (amountToTransfer.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cannot transfer not positive amount: amount=" + amountToTransfer);
        }
        if (accountFrom.getMoneyAmount().compareTo(amountToTransfer) < 0) {
            throw new IllegalArgumentException(
                    "Cannot transfer from account: id=%s, moneyAmount=%s, attemptedTransfer=%s"
                            .formatted(accountFrom, accountFrom.getMoneyAmount(), amountToTransfer)
            );
        }

        BigDecimal totalAmountToDeposit = accountTo.getUserId() != accountFrom.getUserId()
                ? amountToTransfer.multiply(BigDecimal.ONE.subtract(accountProperties.getTransferCommission()))
                : amountToTransfer;

        accountFrom.setMoneyAmount(accountFrom.getMoneyAmount().subtract(amountToTransfer));
        accountTo.setMoneyAmount(accountTo.getMoneyAmount().add(totalAmountToDeposit));
    }

}
