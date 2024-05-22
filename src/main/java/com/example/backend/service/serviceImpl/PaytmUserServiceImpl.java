package com.example.backend.service.serviceImpl;

import com.example.backend.dto.paytmUser.SigninRequest;
import com.example.backend.dto.paytmUser.SignupRequest;
import com.example.backend.dto.paytmUser.SignupResponse;
import com.example.backend.entity.PaytmUser;
import com.example.backend.jwt.service.JwtService;
import com.example.backend.repository.PaytmUserRepo;
import com.example.backend.service.PaytmUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaytmUserServiceImpl implements PaytmUserService {

    private final PaytmUserRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String userSignup(SignupRequest request) throws IllegalArgumentException {
        validateUsernameAlreadyExist(request.getUsername());
        PaytmUser user = new PaytmUser(request.getUsername(), passwordEncoder.encode(request.getPassword()),
                request.getFirstName(), request.getLastName());
        repository.save(user);
        return jwtService.generateToken(user);
    }

    @Override
    public String userSignin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        PaytmUser user = validateUser(request.getUsername());
        return jwtService.generateToken(user);
    }

    private PaytmUser validateUser(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user with username " + username + " not found"));
    }

    private void validateUsernameAlreadyExist(String username) throws IllegalArgumentException {
        List<PaytmUser> paytmUsers = repository.findAll();
        for(PaytmUser user : paytmUsers){
            if(user.getUsername().equals(username)){
                throw new IllegalArgumentException("email id already taken");
            }
        }
    }
}
