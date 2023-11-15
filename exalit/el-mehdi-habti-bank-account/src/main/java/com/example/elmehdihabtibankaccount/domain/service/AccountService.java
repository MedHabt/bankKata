package com.example.elmehdihabtibankaccount.domain.service;

import com.example.elmehdihabtibankaccount.domain.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    void depot(String accountId, double amount);
    void retrait(String accountId, double amount);
    Optional<Double> getBalance(String accountId);
    List<Transaction> getTransactions(String accountId);
}
