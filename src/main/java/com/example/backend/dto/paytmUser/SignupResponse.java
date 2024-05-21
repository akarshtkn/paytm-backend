package com.example.backend.dto.paytmUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {
    private String message;

    private String jwt;

    private String username;

    private String firstName;

    private String lastName;
}
