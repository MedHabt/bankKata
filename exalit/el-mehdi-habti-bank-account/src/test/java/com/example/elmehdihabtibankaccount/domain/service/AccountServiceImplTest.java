package com.example.elmehdihabtibankaccount.domain.service;

import com.example.elmehdihabtibankaccount.domain.adapter.AccountAdapter;
import com.example.elmehdihabtibankaccount.domain.adapter.TransactionAdapter;
import com.example.elmehdihabtibankaccount.domain.exception.AccountNotFoundException;
import com.example.elmehdihabtibankaccount.domain.exception.InsufficientFundsException;
import com.example.elmehdihabtibankaccount.domain.model.Account;
import com.example.elmehdihabtibankaccount.domain.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountAdapter accountAdapter;

    @Mock
    private TransactionAdapter transactionAdapter;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setAccountId("1");
        account.setBalance(2000);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_get_accountById_account_not_found() {
        //given
        String accountId = "111";
        //when
        when(accountAdapter.findById(accountId)).thenReturn(Optional.empty());
        //then
        assertThrows(AccountNotFoundException.class, () -> accountService.depot(accountId, 100.0));
    }

    @Test
    public void test_depot_ok() {
        //given
        double amount = 100.0;
        //account exist
        when(accountAdapter.findById(account.getAccountId())).thenReturn(Optional.of(account));

        //when
        accountService.depot(account.getAccountId(), amount);

        //then
        verify(accountAdapter, times(1)).save(account);
        verify(transactionAdapter, times(1)).save(any(Transaction.class));
    }

    @Test
    public void test_depot_amount_and_check_final_amount_passed() {
        //given
        double balanceInitial = account.getBalance();
        double depotAmount = 100.0;
        System.out.println("balance initial : " + account.getBalance());

        //when
        when(accountAdapter.findById(account.getAccountId())).thenReturn(Optional.of(account));

        //then
        accountService.depot(account.getAccountId(), depotAmount);
        System.out.println("balance final: " + account.getBalance());
        assertEquals(balanceInitial + depotAmount, account.getBalance());
        verify(accountAdapter, times(1)).save(account);
        verify(transactionAdapter, times(1)).save(any(Transaction.class));
    }

    @Test
    public void test_retrait_with_sufficient_funds() {
        //given
        double amount = 50.0;
        //when
        when(accountAdapter.findById(account.getAccountId())).thenReturn(Optional.of(account));
        accountService.retrait(account.getAccountId(), amount);
        //then
        verify(accountAdapter, times(1)).save(account);
        verify(transactionAdapter, times(1)).save(any(Transaction.class));
    }

    @Test
    void test_retrait_with_insufficient_funds() {
        //given
        double amount = 3000.0;
        //when
        when(accountAdapter.findById(account.getAccountId())).thenReturn(Optional.of(account));
        //then
        assertThrows(InsufficientFundsException.class, () -> accountService.retrait(account.getAccountId(), amount));
        assertEquals(2000.0, account.getBalance());
        verify(transactionAdapter, times(0)).save(any(Transaction.class));
        verify(accountAdapter, times(0)).save(account);
    }

    @Test
    void test_getBalance_ok() {
        //given
        when(accountAdapter.findById(account.getAccountId())).thenReturn(Optional.of(account));
        //when
        Optional<Double> result = accountService.getBalance(account.getAccountId());
        //then
        assertTrue(result.isPresent());
        assertEquals(account.getBalance(), result.get());
    }

    @Test
    public void test_getBalance_account_not_found_not_ok() {
        //given
        String accountId = "999";
        //when
        when(accountAdapter.findById(accountId)).thenReturn(Optional.empty());
        //then
        Optional<Double> result = accountService.getBalance(accountId);
        assertTrue(result.isEmpty());
    }

    @Test
    void test_getTransactions_ok() {
        //given
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        transactions.add(new Transaction());
        //when
        when(transactionAdapter.findByAccountId(account.getAccountId())).thenReturn(transactions);
        List<Transaction> result = accountService.getTransactions(account.getAccountId());
        //then
        assertEquals(2, result.size());
    }

    @Test
    void test_getTransactions_no_transactions() {
        //given - when
        when(transactionAdapter.findByAccountId(account.getAccountId())).thenReturn(new ArrayList<>());
        List<Transaction> result = accountService.getTransactions(account.getAccountId());
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    void test_getTransactions_with_invalid_accountId() {
        //given
        String invalidAccountId = "";
        //when - then
        assertThrows(AccountNotFoundException.class, () -> accountService.getTransactions(invalidAccountId));
        verify(transactionAdapter, never()).findByAccountId(anyString());
    }
}