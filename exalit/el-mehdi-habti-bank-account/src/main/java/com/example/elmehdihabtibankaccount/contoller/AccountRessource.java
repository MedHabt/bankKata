package com.example.elmehdihabtibankaccount.contoller;

import com.example.elmehdihabtibankaccount.domain.model.Transaction;
import com.example.elmehdihabtibankaccount.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountRessource {

    private final AccountService accountService;

    @PostMapping("/{accountId}/depot")
    public ResponseEntity<String> depot(@PathVariable String accountId, @RequestParam double amount) {
        accountService.depot(accountId, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{accountId}/retrait")
    public ResponseEntity<String> retrait(@PathVariable String accountId, @RequestParam double amount) {
        accountService.retrait(accountId, amount);
        return ResponseEntity.ok("retrait successful");
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> checkBalance(@PathVariable String accountId) {
        double balance = accountService.getBalance(accountId).orElse(0.0);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountId) {
        List<Transaction> transactions = accountService.getTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }
}
