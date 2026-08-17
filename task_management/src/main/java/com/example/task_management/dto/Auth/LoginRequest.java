package com.example.task_management.dto.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
   
    @NotBlank(message =  "Email Should not be Empty")
    @Email
    private String email;

    @NotBlank
    private String password;

}
