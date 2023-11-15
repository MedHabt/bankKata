package com.example.elmehdihabtibankaccount.infra.driven;

import com.example.elmehdihabtibankaccount.domain.model.Account;
import com.example.elmehdihabtibankaccount.domain.model.Transaction;
import com.example.elmehdihabtibankaccount.domain.model.TransactionType;
import com.example.elmehdihabtibankaccount.infra.entite.AccountEntity;
import com.example.elmehdihabtibankaccount.infra.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AccountAdapterImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountAdapterImpl accountAdapterImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_findById() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId("1");
        accountEntity.setBalance(2000);
        //given
        when(accountRepository.findById(accountEntity.getId())).thenReturn(Optional.of(accountEntity));
        //when
        Optional<Account> retrievedAccount = accountAdapterImpl.findById(accountEntity.getId());

        // Assert
        assertThat(retrievedAccount).isPresent();
        assertThat(retrievedAccount.get().getAccountId()).isEqualTo(accountEntity.getId());
        assertThat(retrievedAccount.get().getBalance()).isEqualTo(accountEntity.getBalance());
        verify(accountRepository, times(1)).findById(accountEntity.getId());
    }

    @Test
    void test_save_ok() {
        //given
        Account account = new Account();
        account.setAccountId("1");
        account.setBalance(2000.0);
        Transaction transaction = new Transaction("1", TransactionType.DEPOT, account.getAccountId(), 500.0, LocalDateTime.now());
        account.setTransactions(List.of(transaction));
        doAnswer(invocation ->  null).when(accountRepository).save(any(AccountEntity.class));
        //when
        accountAdapterImpl.save(account);
        //then
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }
}