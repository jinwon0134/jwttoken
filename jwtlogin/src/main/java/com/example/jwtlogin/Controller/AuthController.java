package com.example.jwtlogin.Controller;

import com.example.jwtlogin.RequestDTO.LoginRequestDTO;
import com.example.jwtlogin.RequestDTO.SignupRequestDTO;
import com.example.jwtlogin.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO signupRequest) {
        try {
            userService.registerUser(signupRequest);
            return ResponseEntity.ok("회원가입 성공: " + signupRequest.email());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인 → JWT 반환
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            String token = userService.loginUser(loginRequest);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
