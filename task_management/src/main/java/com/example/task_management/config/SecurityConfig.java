package com.example.task_management.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.task_management.security.JwtAuthenticationFilter;
import com.example.task_management.service.JwtService;

@Configuration
public class SecurityConfig {
    private final JwtService jwtService;

    public SecurityConfig(JwtService jwtService){
        this.jwtService= jwtService;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

       JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService);

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                      "/api/user/register",
                    "/api/user/login"
                ).permitAll()
                .anyRequest().authenticated()
            ).addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );
           
        
        return http.build();


    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
