package com.example.jwtlogin.RequestDTO;

// Java 21 record 사용
public record SignupRequestDTO(
        String email,
        String password,
        String nickname
) {}
