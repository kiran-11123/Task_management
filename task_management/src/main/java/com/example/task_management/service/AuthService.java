package com.example.task_management.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.task_management.dto.Auth.LoginRequest;
import com.example.task_management.dto.Auth.RegisterRequest;
import com.example.task_management.dto.Auth.RegisterResponse;
import com.example.task_management.entity.User;
import com.example.task_management.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {
       
    private final UserRepository user_repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService  jwtService;
    public AuthService(UserRepository userRepository , PasswordEncoder passwordEncoder , JwtService jwtService){
         this.user_repository = userRepository;
         this.passwordEncoder = passwordEncoder;
         this.jwtService= jwtService;
    }

    public RegisterResponse register(RegisterRequest request){
         
        log.info("Registering user with email : {} " , request.getEmail());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = user_repository.save(user);
        RegisterResponse response = new RegisterResponse();
        response.setEmail(savedUser.getEmail());
        response.setName(savedUser.getName());
              log.info("User registered successfully with id: {}", savedUser.getId());

        return response;
    }

    public String login(LoginRequest request){
            log.info("Login attempt for email: {}", request.getEmail());
         
            Optional<User> user = user_repository.findByEmail(request.getEmail());

            if(!user.isPresent()){
                 throw new RuntimeException("Invalid email or password");
            }

            User existingUser = user.get();
            boolean passwordMatches = passwordEncoder.matches(request.getPassword(), existingUser.getPassword());
             if (!passwordMatches) {
        throw new RuntimeException("Invalid email or password");
    }

    RegisterResponse response = new RegisterResponse();
    response.setEmail(existingUser.getEmail());
    response.setName(existingUser.getName());

     log.info("User logged in successfully: {}", request.getEmail());
     String token = jwtService.generateToken(existingUser.getId() , existingUser.getEmail());

    return token;
    } 
} 
