package com.example.backend.dto.paytmUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsUpdateRequest {
    private String password;
    private String firstName;
    private String lastName;
}
