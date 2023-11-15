package com.example.elmehdihabtibankaccount.infra.driven;

import com.example.elmehdihabtibankaccount.domain.adapter.TransactionAdapter;
import com.example.elmehdihabtibankaccount.domain.model.Transaction;
import com.example.elmehdihabtibankaccount.infra.mapper.TransactionMapper;
import com.example.elmehdihabtibankaccount.infra.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TransactionAdapterImpl implements TransactionAdapter {

    private final TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(TransactionMapper.mapToTransactionEntity(transaction));
    }

    @Override
    public List<Transaction> findByAccountId(String accountId) {
        return transactionRepository.findAllByAccountId(accountId).stream()
                .map(TransactionMapper::mapToTransaction)
                .collect(Collectors.toList());
    }
}
