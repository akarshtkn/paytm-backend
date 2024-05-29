package com.example.backend.controller;

import com.example.backend.dto.paytmUser.TransferAmountRequest;
import com.example.backend.service.PaytmUserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
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

    @PostMapping("/transfer")
    public ResponseEntity<String> transferAmount(@RequestBody TransferAmountRequest request) {
        accountService.transferAmount(request.getFromUserId(), request.getToUserId(), request.getAmount());
        return new ResponseEntity<>("Transfer successful", HttpStatus.OK);
    }
}
