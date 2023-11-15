package com.example.elmehdihabtibankaccount.infra.mapper;

import com.example.elmehdihabtibankaccount.domain.model.Transaction;
import com.example.elmehdihabtibankaccount.infra.entite.AccountEntity;
import com.example.elmehdihabtibankaccount.infra.entite.TransactionEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionMapper {

    public static Transaction mapToTransaction(TransactionEntity transactionEntity) {
        if(transactionEntity==null){
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionEntity.getId());
        transaction.setAmount(transactionEntity.getAmount());
        transaction.setTimestamp(transactionEntity.getTimestamp());
        return transaction;
    }

    public static List<Transaction> mapToListTransaction(List<TransactionEntity> TransactionEntities) {
        return TransactionEntities.stream()
                .map(TransactionMapper::mapToTransaction)
                .collect(Collectors.toList());
    }

    public static TransactionEntity mapToTransactionEntity(Transaction transaction) {
        if(transaction==null){
            return null;
        }
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(transaction.getTransactionId());
        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setTimestamp(transaction.getTimestamp());
        return transactionEntity;
    }

    public static List<TransactionEntity> mapToListTransactionEntity(List<Transaction> transactions, AccountEntity accountEntity) {
        return transactions.stream()
                .map(transaction -> {
                    TransactionEntity transactionEntity = mapToTransactionEntity(transaction);
                    transactionEntity.setAccount(accountEntity);
                    return transactionEntity;
                })
                .toList();
    }
}
