package com.example.elmehdihabtibankaccount.domain.adapter;

import com.example.elmehdihabtibankaccount.domain.model.Account;

import java.util.Optional;

public interface AccountAdapter {
    Optional<Account> findById(String accountId);

    void save(Account account);
}
