package com.example.backend.service.serviceImpl;

import com.example.backend.dto.paytmUser.SigninRequest;
import com.example.backend.dto.paytmUser.SignupRequest;
import com.example.backend.entity.PaytmUser;
import com.example.backend.repository.PaytmUserRepo;
import com.example.backend.service.PaytmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaytmUserServiceImpl implements PaytmUserService {

    @Autowired
    public PaytmUserRepo repo;

    @Override
    public PaytmUser userSignup(SignupRequest request) throws IllegalArgumentException {
        validateUsernameAlreadyExist(request.getUsername());
        PaytmUser user = new PaytmUser(request.getUsername(), request.getPassword(),
                request.getFirstName(), request.getLastName());
        return repo.save(user);
    }

    @Override
    public String userSignin(SigninRequest request) {
        PaytmUser user = validateUser(request.getUsername());
        if(request.getPassword().equals(user.getPassword())){
            return "jwt";
        } else {
            throw new IllegalArgumentException("password doesn't match");
        }
    }

    private PaytmUser validateUser(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user with username " + username + " not found"));
    }

    private void validateUsernameAlreadyExist(String username) throws IllegalArgumentException {
        List<PaytmUser> paytmUsers = repo.findAll();
        for(PaytmUser user : paytmUsers){
            if(user.getUsername().equals(username)){
                throw new IllegalArgumentException("email id already taken");
            }
        }
    }
}
