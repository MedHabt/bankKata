package com.example.elmehdihabtibankaccount.contoller;

import com.example.elmehdihabtibankaccount.domain.model.Transaction;
import com.example.elmehdihabtibankaccount.domain.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AccountRessourceTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountRessource accountRessource;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountRessource).build();
    }

    @Test
    void depot() throws Exception {
        mockMvc.perform(post("/accounts/1/depot")
                        .param("amount", "1000.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deposit successful"));
        verify(accountService, times(1)).depot(eq("1"), eq(1000.0));
    }

    @Test
    void retrait() throws Exception {
        mockMvc.perform(post("/accounts/1/retrait")
                        .param("amount", "500.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("retrait successful"));

        verify(accountService, times(1)).retrait(eq("1"), eq(500.0));
    }

    @Test
    void checkBalance() throws Exception {
        when(accountService.getBalance(eq("1"))).thenReturn(Optional.of(5000.0));

        mockMvc.perform(get("/accounts/1/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("5000.0"));
    }

    @Test
    void getTransactions() throws Exception {
        when(accountService.getTransactions(eq("1"))).thenReturn(Collections.singletonList(new Transaction()));

        mockMvc.perform(get("/accounts/1/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}