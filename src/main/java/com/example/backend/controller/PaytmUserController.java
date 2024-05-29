package com.example.backend.controller;

import com.example.backend.dto.paytmUser.*;
import com.example.backend.entity.PaytmUser;
import com.example.backend.jwt.service.JwtService;
import com.example.backend.service.PaytmUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class PaytmUserController {

    private final PaytmUserService service;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> userSignup(@RequestBody SignupRequest request)
            throws IllegalArgumentException {
        PaytmUser newUser = service.userSignup(request);
        String token = jwtService.generateToken(newUser);
        UserDetailsResponse newUserDto = modelMapper.map(newUser, UserDetailsResponse.class);
        return new ResponseEntity<>(
                new SignupResponse(newUserDto, token), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignupResponse> userSignin(@RequestBody SigninRequest request) {
        PaytmUser user = service.userSignin(request);
        String token = jwtService.generateToken(user);
        UserDetailsResponse newUserDto = modelMapper.map(user, UserDetailsResponse.class);
        return new ResponseEntity<>(new SignupResponse(newUserDto, token), HttpStatus.OK);
    }

//    @GetMapping("/getAll")
//    public ResponseEntity<List<UserDetailsResponse>> getAllUsers() {
//        List<PaytmUser> users = service.getAllUsers();
//        List<UserDetailsResponse> usersDto = users.stream()
//                .map(user -> modelMapper.map(user, UserDetailsResponse.class))
//                .toList();
//        return new ResponseEntity<>(usersDto, HttpStatus.OK);
//    }

    @GetMapping("/get")
    public ResponseEntity<List<UserDetailsResponse>> getUser(@RequestParam(required = false) String filter,
                                                             @RequestParam Integer userId) {
        PaytmUser currentUser = service.findUserById(userId);
        List<PaytmUser> users;
        if (filter == null || filter.isEmpty()) {
            users = service.getAllUsers();
        } else {
            users = service.getUserByFilter(filter);
        }
        users.remove(currentUser);
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
