package com.example.backend.service.serviceImpl;

import com.example.backend.dto.paytmUser.SigninRequest;
import com.example.backend.dto.paytmUser.SignupRequest;
import com.example.backend.dto.paytmUser.UserDetailsUpdateRequest;
import com.example.backend.entity.PaytmUser;
import com.example.backend.jwt.service.JwtService;
import com.example.backend.repository.PaytmUserRepository;
import com.example.backend.service.PaytmUserAccountService;
import com.example.backend.service.PaytmUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaytmUserServiceImpl implements PaytmUserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final PaytmUserRepository repository;
    private final JwtService jwtService;
    private final PaytmUserAccountService accountService;

    @Override
    public String userSignup(SignupRequest request) throws IllegalArgumentException {
        validateUsernameAlreadyExist(request.getUsername());
        PaytmUser user = new PaytmUser(request.getUsername(), passwordEncoder.encode(request.getPassword()),
                request.getFirstName(), request.getLastName());
        PaytmUser createdUser = repository.save(user);
        accountService.setInitialBalance(createdUser);
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

    @Override
    public List<PaytmUser> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public List<PaytmUser> getUserByFilter(String filter) {
        return repository.getUserByFirstName(filter);
    }

    @Override
    public void updateUserDetails(Integer userId, UserDetailsUpdateRequest request) {
        PaytmUser user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user with id " + userId + " not found"));
        if(!request.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if(!request.getFirstName().isBlank()){
            user.setFirstName(request.getFirstName());
        }
        if(!request.getLastName().isBlank()){
            user.setLastName(request.getLastName());
        }
        repository.save(user);
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
