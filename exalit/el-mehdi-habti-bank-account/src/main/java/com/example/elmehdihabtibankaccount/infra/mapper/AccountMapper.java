package com.example.elmehdihabtibankaccount.infra.mapper;

import com.example.elmehdihabtibankaccount.domain.model.Account;
import com.example.elmehdihabtibankaccount.infra.entite.AccountEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountMapper {

    public static Account mapToAccount(AccountEntity accountEntity) {
        if(accountEntity==null){
            return null;
        }
        Account account = new Account();
        account.setAccountId(accountEntity.getId());
        account.setBalance(accountEntity.getBalance());
        account.setTransactions(TransactionMapper.mapToListTransaction(accountEntity.getTransactions()));
        return account;
    }

    public static AccountEntity mapToAccountEntity(Account account) {
        if(account==null){
            return null;
        }
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(account.getAccountId());
        accountEntity.setBalance(account.getBalance());
        accountEntity.setTransactions(TransactionMapper.mapToListTransactionEntity(account.getTransactions(), accountEntity));
        return accountEntity;
    }
}
