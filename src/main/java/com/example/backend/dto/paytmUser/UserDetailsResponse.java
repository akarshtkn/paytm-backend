package com.example.backend.dto.paytmUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
}
