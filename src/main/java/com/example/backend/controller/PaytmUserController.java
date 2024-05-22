package com.example.backend.controller;

import com.example.backend.dto.paytmUser.SigninRequest;
import com.example.backend.dto.paytmUser.SigninResponse;
import com.example.backend.dto.paytmUser.SignupResponse;
import com.example.backend.dto.paytmUser.SignupRequest;
import com.example.backend.service.PaytmUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class PaytmUserController {

    private final PaytmUserService service;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> userSignup(@RequestBody SignupRequest request)
            throws IllegalArgumentException {
        String token = service.userSignup(request);
        return new ResponseEntity<>(
                new SignupResponse("User created successfully", token), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> userSignin(@RequestBody SigninRequest request) {
        String token = service.userSignin(request);
        return new ResponseEntity<>(
                new SigninResponse(token), HttpStatus.OK
        );
    }
}
