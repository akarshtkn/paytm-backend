package com.example.backend.repository;

import com.example.backend.entity.PaytmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaytmUserRepository extends JpaRepository<PaytmUser, Integer> {

    @Query(value = "SELECT * FROM paytm_user WHERE username=:username", nativeQuery = true)
    Optional<PaytmUser> findByUsername(String username);

    @Query(value = "SELECT * FROM paytm_user WHERE first_name LIKE :filter%", nativeQuery = true)
    List<PaytmUser> getUserByFirstName(String filter);
}
