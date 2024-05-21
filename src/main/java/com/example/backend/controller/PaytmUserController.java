package com.example.backend.controller;

import com.example.backend.dto.paytmUser.SigninRequest;
import com.example.backend.dto.paytmUser.SigninResponse;
import com.example.backend.dto.paytmUser.SignupResponse;
import com.example.backend.dto.paytmUser.SignupRequest;
import com.example.backend.entity.PaytmUser;
import com.example.backend.service.PaytmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class PaytmUserController {

    @Autowired
    public PaytmUserService service;

    @PostMapping("/signup")
    public SignupResponse userSignup(@RequestBody SignupRequest request) throws IllegalArgumentException {
        PaytmUser createdUser = service.userSignup(request);
        return new SignupResponse("User created successfully", "jwt", createdUser.getUsername(),
                createdUser.getFirstName(), createdUser.getLastName());
    }

    @PostMapping("signin")
    public SigninResponse userSignin(@RequestBody SigninRequest request) {
        String response = service.userSignin(request);
        return new SigninResponse(response);
    }
}
