package com.example.elmehdihabtibankaccount.infra.driven;

import com.example.elmehdihabtibankaccount.domain.adapter.AccountAdapter;
import com.example.elmehdihabtibankaccount.domain.model.Account;
import com.example.elmehdihabtibankaccount.infra.mapper.AccountMapper;
import com.example.elmehdihabtibankaccount.infra.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AccountAdapterImpl implements AccountAdapter {

    private final AccountRepository accountRepository;

    @Override
    public Optional<Account> findById(String accountId) {
        return accountRepository.findById(accountId)
                .map(AccountMapper::mapToAccount);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(AccountMapper.mapToAccountEntity(account));
    }
}
