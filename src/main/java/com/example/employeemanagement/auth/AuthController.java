package com.example.employeemanagement.auth;

import com.example.employeemanagement.dto.LoginRequest;
import com.example.employeemanagement.dto.RegisterRequest;
import com.example.employeemanagement.entity.Role;
import com.example.employeemanagement.entity.User;
import com.example.employeemanagement.exception.AuthenticationException;
import com.example.employeemanagement.repository.UserRepository;
import com.example.employeemanagement.response.ApiResponse;
import com.example.employeemanagement.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ApiResponse<String> register(

            @Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return new ApiResponse<>("SUCCESS",
                "User registered successfully",
                null);
    }

    @PostMapping("/register-admin")
    public ApiResponse<String> registerAdmin(

            @Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        return new ApiResponse<>("SUCCESS",
                "Admin registered successfully",
                null);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(
            @Valid @RequestBody LoginRequest request) {

        User user = userRepository
                .findByUsernameIgnoreCase(request.getUsername())
                .orElseThrow(() ->
                        new AuthenticationException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new AuthenticationException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new ApiResponse<>("SUCCESS",
                "Login successful",
                token);
    }
}