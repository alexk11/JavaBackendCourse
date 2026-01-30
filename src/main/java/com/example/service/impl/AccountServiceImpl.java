package com.example.service.impl;

import com.example.model.AccountProperties;
import com.example.persistence.entity.Account;
import com.example.persistence.entity.User;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountProperties accountProperties;
    private final SessionFactory sessionFactory;
    private final TransactionService transactionService;

    @Override
    public List<Account> getAllAccounts() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("SELECT a FROM Account a", Account.class)
                    .list();
        }
    }

    @Override
    public Account createAccount(User user) {
        return transactionService.executeInTransactionGeneric(() -> {
            Account newAccount = Account.builder()
                    .user(user)
                    .moneyAmount(accountProperties.getDefaultAmount())
                    .build();
            sessionFactory.getCurrentSession().persist(newAccount);
            return newAccount;
        });
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        var account = sessionFactory.getCurrentSession()
                .get(Account.class, id);
        return Optional.of(account);
    }

    private Optional<Account> getAccountById2(Long id) {
        var account = sessionFactory.getCurrentSession()
                .get(Account.class, id);
        return Optional.of(account);
    }

    @Override
    public List<Account> getUserAccounts(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("SELECT a FROM Account a WHERE a.user.id = :userId", Account.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    @Override
    public void depositAccount(Long accountId, BigDecimal moneyToDeposit) {
        if (moneyToDeposit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Cannot deposit not positive amount = " + moneyToDeposit);
        }
        transactionService.executeInTransaction(session -> {
            Account account = session.get(Account.class, accountId);
            if (account == null) {
                throw new IllegalArgumentException("No such account: id=" + accountId);
            }
            account.setMoneyAmount(account.getMoneyAmount().add(moneyToDeposit));
            session.persist(account);
        });
    }

    @Override
    public void withdrawFromAccount(Long accountId, BigDecimal amountToWithdraw) {
        if (amountToWithdraw.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Cannot withdraw not positive amount: amount=" + amountToWithdraw);
        }
        transactionService.executeInTransaction(session -> {
            Account account = session.get(Account.class, accountId);
            if (account == null) {
                throw new IllegalArgumentException("No account with the id=" + accountId);
            }
            if (account.getMoneyAmount().compareTo(amountToWithdraw) < 0) {
                throw new IllegalArgumentException(
                        "Not enough money to withdraw: id=%s, moneyAmount=%s, attemptedWithdraw=%s"
                                .formatted(accountId, account.getMoneyAmount(), amountToWithdraw)
                );
            }
            account.setMoneyAmount(account.getMoneyAmount().subtract(amountToWithdraw));
            session.persist(account);
        });
    }

    @Override
    public void closeAccount(Long accountId) {
        transactionService.executeInTransaction(session -> {
            Account accountToRemove = session.get(Account.class, accountId);
            if (accountToRemove == null) {
                throw new IllegalArgumentException("No such account: id=" + accountId);
            }
            int count = session.createQuery(
                    "SELECT a FROM Account a", Account.class).list().size();
            if (count == 1) {
                throw new IllegalArgumentException(
                        "Cannot close the only one user's account");
            }
            Account accountToDeposit = session
                    .createQuery("SELECT a FROM Account a WHERE a.user.id = :userId " +
                            "AND a.id <> :accountId", Account.class)
                        .setParameter("userId", accountToRemove.getUser().getId())
                        .setParameter("accountId", accountId)
                        .list().stream()
                        .findFirst()
                        .orElseThrow();

            accountToDeposit.setMoneyAmount(
                    accountToDeposit.getMoneyAmount()
                            .add(accountToRemove.getMoneyAmount()));

            session.persist(accountToDeposit);
            session.remove(accountToRemove);
        });
    }

    @Override
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amountToTransfer) {
        if (amountToTransfer.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Cannot transfer not positive amount: amount = " + amountToTransfer);
        }
        transactionService.executeInTransaction(session -> {
            Account accountFrom = session.get(Account.class, fromAccountId);
            if (accountFrom == null) {
                throw new IllegalArgumentException("No account with the id=" + fromAccountId);
            }
            Account accountTo = session.get(Account.class, toAccountId);
            if (accountTo == null) {
                throw new IllegalArgumentException("No account with the id=" + toAccountId);
            }
            if (accountFrom.getMoneyAmount().compareTo(amountToTransfer) < 0) {
                throw new IllegalArgumentException(
                        "Cannot transfer from account: id=%s, moneyAmount=%s, attemptedTransfer=%s"
                                .formatted(accountFrom, accountFrom.getMoneyAmount(), amountToTransfer)
                );
            }
            BigDecimal totalAmountToDeposit = !Objects.equals(accountTo.getUser().getId(), accountFrom.getUser().getId())
                    ? amountToTransfer.multiply(BigDecimal.ONE.subtract(accountProperties.getTransferCommission()))
                    : amountToTransfer;
            accountFrom.setMoneyAmount(accountFrom.getMoneyAmount().subtract(amountToTransfer));
            accountTo.setMoneyAmount(accountTo.getMoneyAmount().add(totalAmountToDeposit));

            session.persist(accountFrom);
            session.persist(accountTo);
        });
    }

}
