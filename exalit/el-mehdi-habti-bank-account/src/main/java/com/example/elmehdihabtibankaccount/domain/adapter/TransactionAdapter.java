package com.example.elmehdihabtibankaccount.domain.adapter;

import com.example.elmehdihabtibankaccount.domain.model.Transaction;

import java.util.List;

public interface TransactionAdapter {
    void save(Transaction transaction);

    List<Transaction> findByAccountId(String accountId);
}
