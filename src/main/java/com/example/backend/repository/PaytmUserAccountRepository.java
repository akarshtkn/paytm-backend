package com.example.backend.repository;

import com.example.backend.entity.PaytmUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaytmUserAccountRepository extends JpaRepository<PaytmUserAccount, Integer> {
}
