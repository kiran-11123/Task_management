package com.example.task_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_management.dto.ApiResponse;
import com.example.task_management.dto.Auth.LoginRequest;
import com.example.task_management.dto.Auth.RegisterRequest;
import com.example.task_management.dto.Auth.RegisterResponse;
import com.example.task_management.entity.User;
import com.example.task_management.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/api/user")
@Slf4j
public class AuthController {

     private final AuthService authService;
     public AuthController(AuthService authService){
        this.authService = authService;
     }

     @PostMapping("/register")
     public ResponseEntity<ApiResponse<RegisterResponse>>  registerUser(@RequestBody RegisterRequest request){
           log.info("Register API called");
           RegisterResponse response = authService.register(request);
           ApiResponse<RegisterResponse> result = new ApiResponse<>(200 , "User Registered Successfully" , response);
           return ResponseEntity.ok(result);
     }

     @PostMapping("/login")
     public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody LoginRequest request , HttpServletResponse response){
           
        log.info("Login API Called");

        String token = authService.login(request);
        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true in HTTPS production
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        ApiResponse<String> result= new ApiResponse<>(200 , "User LoggedIn Suuccessfully" , "");
        return ResponseEntity.ok(result);   
     }

}
