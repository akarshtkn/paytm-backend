package com.example.backend.service;

import com.example.backend.dto.paytmUser.TransferAmountRequest;
import com.example.backend.entity.PaytmUser;


public interface PaytmUserAccountService {

    Integer getAccountBalance(Integer userId);

    void transferAmount(Integer fromUserId, Integer toUserId, Integer amount);
}
