package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.AuthInfoResponse;
import com.example.employeemanagement.response.ApiResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthInfoController {

    @GetMapping("/me")
    public ApiResponse<AuthInfoResponse> getCurrentUser(Authentication authentication) {

        String username = authentication.getName();

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        AuthInfoResponse response =
                new AuthInfoResponse(username, roles);

        return new ApiResponse<>(
                "SUCCESS",
                "Authenticated user details fetched successfully",
                response
        );
    }
}