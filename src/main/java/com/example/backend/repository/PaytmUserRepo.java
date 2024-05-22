package com.example.backend.repository;

import com.example.backend.entity.PaytmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaytmUserRepo extends JpaRepository<PaytmUser, Integer> {

    @Query(value = "SELECT * FROM paytm_user WHERE username=:username", nativeQuery = true)
    Optional<PaytmUser> findByUsername(String username);
}
