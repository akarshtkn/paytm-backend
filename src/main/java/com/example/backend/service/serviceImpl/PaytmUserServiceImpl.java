package com.example.backend.service.serviceImpl;

import com.example.backend.dto.paytmUser.SigninRequest;
import com.example.backend.dto.paytmUser.SignupRequest;
import com.example.backend.dto.paytmUser.UserDetailsUpdateRequest;
import com.example.backend.entity.PaytmUser;
import com.example.backend.entity.PaytmUserAccount;
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

    @Override
    public String userSignup(SignupRequest request) throws IllegalArgumentException {
        validateUsernameAlreadyExist(request.getUsername());
        PaytmUser user = new PaytmUser(request.getUsername(), passwordEncoder.encode(request.getPassword()),
                request.getFirstName(), request.getLastName());
        PaytmUserAccount account = new PaytmUserAccount();
        account.setAccountBalance((int) (1 + Math.random() * 10000));
        user.setAccount(account);
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
        PaytmUser user = validateUserByUsername(request.getUsername());
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

    @Override
    public PaytmUser findUserById(Integer userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user with id " + userId + " not found"));
    }

    @Override
    public void saveTransaction(List<PaytmUser> users) {
        repository.saveAll(users);
    }

    private PaytmUser validateUserByUsername(String username) {
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
