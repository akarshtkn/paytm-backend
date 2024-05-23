package com.example.backend.service;

import com.example.backend.entity.PaytmUser;


public interface PaytmUserAccountService {
    void setInitialBalance(PaytmUser user);

    Integer getAccountBalance(Integer userId);
}
