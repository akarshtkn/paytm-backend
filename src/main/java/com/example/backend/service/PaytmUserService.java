package com.example.backend.service;

import com.example.backend.dto.paytmUser.SigninRequest;
import com.example.backend.dto.paytmUser.SignupRequest;

public interface PaytmUserService {

    String userSignup(SignupRequest request) throws IllegalArgumentException;

    String userSignin(SigninRequest request);
}
