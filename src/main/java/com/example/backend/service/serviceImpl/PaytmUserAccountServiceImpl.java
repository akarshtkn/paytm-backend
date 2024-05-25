package com.example.backend.service.serviceImpl;

import com.example.backend.entity.PaytmUser;
import com.example.backend.service.PaytmUserAccountService;
import com.example.backend.service.PaytmUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaytmUserAccountServiceImpl implements PaytmUserAccountService {

    private final PaytmUserService userService;

    @Override
    public Integer getAccountBalance(Integer userId) {
        PaytmUser user = userService.findUserById(userId);
        return user.getAccount().getAccountBalance();
    }

    @Override
    @Transactional
    public void transferAmount(Integer fromUserId,
                               Integer toUserId,
                               Integer amount) {
        PaytmUser fromUser = userService.findUserById(fromUserId);
        if(fromUser.getAccount().getAccountBalance() < amount){
            throw new RuntimeException("Insufficient balance");
        }
        PaytmUser toUser = userService.findUserById(toUserId);
        fromUser.getAccount().setAccountBalance(
                fromUser.getAccount().getAccountBalance() - amount
        );
        toUser.getAccount().setAccountBalance(
                toUser.getAccount().getAccountBalance() + amount
        );
        List<PaytmUser> users = new ArrayList<>();
        users.add(toUser);
        users.add(fromUser);
        userService.saveTransaction(users);
    }

}
