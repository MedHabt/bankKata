package com.example.elmehdihabtibankaccount.infra.repository;

import com.example.elmehdihabtibankaccount.infra.entite.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
}
