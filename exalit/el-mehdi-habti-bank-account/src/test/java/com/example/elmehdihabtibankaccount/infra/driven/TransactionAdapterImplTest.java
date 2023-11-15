package com.example.elmehdihabtibankaccount.infra.driven;

import com.example.elmehdihabtibankaccount.domain.model.Transaction;
import com.example.elmehdihabtibankaccount.domain.model.TransactionType;
import com.example.elmehdihabtibankaccount.infra.entite.AccountEntity;
import com.example.elmehdihabtibankaccount.infra.entite.TransactionEntity;
import com.example.elmehdihabtibankaccount.infra.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class TransactionAdapterImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionAdapterImpl transactionAdapterImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_save_ok() {
        Transaction transaction = new Transaction("1", TransactionType.DEPOT, "1", 500.0, LocalDateTime.now());
        doAnswer(invocation -> null).when(transactionRepository).save(any(TransactionEntity.class));
        transactionAdapterImpl.save(transaction);
        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }

    @Test
    void test_find_ByAccountId() {
        //given
        List<TransactionEntity> transactionEntities = new ArrayList<>();
        transactionEntities.add(new TransactionEntity("1", 500.0, LocalDateTime.now(), TransactionType.DEPOT, new AccountEntity("1",5000.0,new ArrayList<>())));
        when(transactionRepository.findAllByAccountId("1")).thenReturn(transactionEntities);
        //when
        List<Transaction> transactions = transactionAdapterImpl.findByAccountId("1");
        //then
        verify(transactionRepository, times(1)).findAllByAccountId("1");
    }
}