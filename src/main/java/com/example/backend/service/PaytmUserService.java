package com.example.backend.service;

import com.example.backend.dto.paytmUser.SigninRequest;
import com.example.backend.dto.paytmUser.SignupRequest;
import com.example.backend.entity.PaytmUser;

public interface PaytmUserService {

    PaytmUser userSignup(SignupRequest request) throws IllegalArgumentException;

    String userSignin(SigninRequest request);
}
