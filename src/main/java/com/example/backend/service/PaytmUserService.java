package com.example.backend.service;

import com.example.backend.dto.paytmUser.SigninRequest;
import com.example.backend.dto.paytmUser.SignupRequest;
import com.example.backend.dto.paytmUser.UserDetailsUpdateRequest;
import com.example.backend.entity.PaytmUser;

import java.util.List;

public interface PaytmUserService {

    String userSignup(SignupRequest request) throws IllegalArgumentException;

    String userSignin(SigninRequest request);

    List<PaytmUser> getAllUsers();

    List<PaytmUser> getUserByFilter(String filter);

    void updateUserDetails(Integer userId, UserDetailsUpdateRequest request);
}
