package com.example.elmehdihabtibankaccount.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//account of the user
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    //Identifiant du compte (surtout côté bd)
    private String accountId;
    //la somme dans le compte
    private double balance;
    //Liste de transaction effectuées
    private List<Transaction> transactions = new ArrayList<>();
}
