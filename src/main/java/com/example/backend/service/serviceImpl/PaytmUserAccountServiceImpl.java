package com.example.backend.service.serviceImpl;

import com.example.backend.entity.PaytmUser;
import com.example.backend.entity.PaytmUserAccount;
import com.example.backend.repository.PaytmUserAccountRepository;
import com.example.backend.service.PaytmUserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaytmUserAccountServiceImpl implements PaytmUserAccountService {

    private final PaytmUserAccountRepository repository;

    @Override
    public void setInitialBalance(PaytmUser user) {
        PaytmUserAccount account = new PaytmUserAccount();
        account.setPaytmUser(user);
        account.setAccountBalance((int) (1 + Math.random() * 10000));
        repository.save(account);
    }

    @Override
    public Integer getAccountBalance(Integer userId) {
        PaytmUser user =
    }
}
