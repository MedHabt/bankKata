package com.example.elmehdihabtibankaccount.infra.entite;

import com.example.elmehdihabtibankaccount.domain.model.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    @Id
    @NotNull
    private String id;
    @NotNull
    @NotEmpty
    private double amount;
    @DateTimeFormat
    @NotNull
    @NotEmpty
    private LocalDateTime timestamp;
    @NotNull
    @NotEmpty
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}
