package com.example.employeemanagement.dto;

import java.util.List;

public class AuthInfoResponse {

    private String username;
    private List<String> roles;

    public AuthInfoResponse(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}