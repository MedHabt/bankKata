package com.example.elmehdihabtibankaccount.infra.repository;

import com.example.elmehdihabtibankaccount.infra.entite.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
    List<TransactionEntity> findAllByAccountId(String accountEntityId);

}
