package com.example.elmehdihabtibankaccount.domain.service;

import com.example.elmehdihabtibankaccount.domain.adapter.AccountAdapter;
import com.example.elmehdihabtibankaccount.domain.adapter.TransactionAdapter;
import com.example.elmehdihabtibankaccount.domain.exception.AccountNotFoundException;
import com.example.elmehdihabtibankaccount.domain.exception.InsufficientFundsException;
import com.example.elmehdihabtibankaccount.domain.model.Account;
import com.example.elmehdihabtibankaccount.domain.model.Transaction;
import com.example.elmehdihabtibankaccount.domain.model.TransactionType;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountAdapter accountAdapter;
    private final TransactionAdapter transactionAdapter;

    @Override
    public void depot(String accountId, double amount) {
        validateAccountId(accountId);
        validatePositiveAmount(amount);

        accountAdapter.findById(accountId)
                .ifPresentOrElse(account -> {
                    account.setBalance(account.getBalance() + amount);
                    Transaction transaction = createTransaction(accountId, amount, TransactionType.DEPOT);
                    transactionAdapter.save(transaction);
                    accountAdapter.save(account);
                }, () -> {
                    throw new AccountNotFoundException("Account not found.");
                });
    }

    @Override
    public void retrait(String accountId, double amount) {
        validateAccountId(accountId);
        validatePositiveAmount(amount);

        accountAdapter.findById(accountId)
                .filter(account -> account.getBalance() >= amount)
                .ifPresentOrElse(account -> {
                    account.setBalance(account.getBalance() - amount);
                    Transaction transaction = createTransaction(accountId, -amount, TransactionType.RETRAIT);
                    transactionAdapter.save(transaction);
                    accountAdapter.save(account);
                }, () -> {
                    throw new InsufficientFundsException("Insufficient funds for retrait.");
                });
    }

    @Override
    public Optional<Double> getBalance(String accountId) {
        validateAccountId(accountId);
        return accountAdapter.findById(accountId).map(Account::getBalance);
    }

    @Override
    public List<Transaction> getTransactions(String accountId) {
        validateAccountId(accountId);
        return transactionAdapter.findByAccountId(accountId);
    }

    private void validateAccountId(String accountId) {
        Optional.ofNullable(accountId)
                .filter(StringUtils::isNotEmpty)
                .orElseThrow(() -> new AccountNotFoundException("Account id must not be empty."));
    }

    private void validatePositiveAmount(double amount) {
        Optional.of(amount)
                .filter(value -> value > 0)
                .orElseThrow(() -> new IllegalArgumentException("Amount must be positive."));
    }

    private Transaction createTransaction(String accountId, double amount, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTimestamp(LocalDateTime.now());
        return transaction;
    }
}
