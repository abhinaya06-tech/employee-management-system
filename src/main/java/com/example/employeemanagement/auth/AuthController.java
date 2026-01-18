package com.example.employeemanagement.auth;

import com.example.employeemanagement.entity.User;
import com.example.employeemanagement.repository.UserRepository;
import com.example.employeemanagement.security.JwtUtil;
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
    public String register(@RequestBody User user) {
        user.setPassword(
                passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // ✅ FIX
        userRepository.save(user);
        return "User registered";
    }

    @PostMapping("/register-admin")
    public String registerAdmin(@RequestBody User user) {
        user.setPassword(
                passwordEncoder.encode(user.getPassword()));
        user.setRole("ADMIN"); // ✅ FIX
        userRepository.save(user);
        return "Admin registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody User request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}
