package com.example.elmehdihabtibankaccount.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    //Identifiant transaction
    private String transactionId;
    //Type de transaction
    private TransactionType transactionType;
    //Id du compte
    private String accountId;
    //La somme a verse/retire
    private double amount;
    //date transaction
    private LocalDateTime timestamp;
}
