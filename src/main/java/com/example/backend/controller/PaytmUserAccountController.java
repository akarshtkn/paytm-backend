package com.example.backend.controller;

import com.example.backend.service.PaytmUserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class PaytmUserAccountController {

    private final PaytmUserAccountService accountService;

    @GetMapping("/balance")
    public ResponseEntity<Integer> getAccountBalance(@RequestParam Integer userId) {
        return new ResponseEntity<>(
                accountService.getAccountBalance(userId), HttpStatus.OK
        );
    }
}
