package com.example.backend.controller;

import com.example.backend.dto.paytmUser.*;
import com.example.backend.entity.PaytmUser;
import com.example.backend.service.PaytmUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class PaytmUserController {

    private final PaytmUserService service;
    private final ModelMapper modelMapper;

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
        return new ResponseEntity<>(new SigninResponse(token), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDetailsResponse>> getAllUsers() {
        List<PaytmUser> users = service.getAllUsers();
        List<UserDetailsResponse> usersDto = users.stream()
                .map(user -> modelMapper.map(user, UserDetailsResponse.class))
                .toList();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserDetailsResponse>> getUser(@RequestParam String filter) {
        List<PaytmUser> users = service.getUserByFilter(filter);
        List<UserDetailsResponse> usersDto = users.stream()
                .map(user -> modelMapper.map(user, UserDetailsResponse.class))
                .toList();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUserDetails(@RequestBody UserDetailsUpdateRequest request,
                                                    @RequestParam Integer userId) {
        service.updateUserDetails(userId, request);
        return new ResponseEntity<>("user details updated", HttpStatus.OK);
    }
}
