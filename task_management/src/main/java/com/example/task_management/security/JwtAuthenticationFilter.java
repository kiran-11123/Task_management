package com.example.task_management.security;

import java.io.IOException;

import com.example.task_management.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get cookies
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Find access_token cookie
        String token = null;

        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("access_token")) {
                token = cookie.getValue();
                break;
            }
        }

        // 3. No token
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            // 4. Extract email from JWT
            String email = jwtService.extractEmail(token);

            // 5. Create authentication
            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                null
                        );

                // 6. Store authentication
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }

        } catch (Exception e) {

            System.out.println("Invalid JWT: " + e.getMessage());
        }

        // 7. Continue request
        filterChain.doFilter(request, response);
    }
}