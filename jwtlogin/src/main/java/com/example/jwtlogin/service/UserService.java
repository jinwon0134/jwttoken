package com.example.jwtlogin.service;

import com.example.jwtlogin.RequestDTO.LoginRequestDTO;
import com.example.jwtlogin.RequestDTO.SignupRequestDTO;
import com.example.jwtlogin.Entity.User;
import com.example.jwtlogin.Repository.UserRepository;
import com.example.jwtlogin.Util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // 회원가입
    public User registerUser(SignupRequestDTO signupRequest) {
        if (userRepository.existsByemail(signupRequest.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.password());
        User user = new User(
                signupRequest.email(),
                encodedPassword,
                signupRequest.nickname()
        );

        return userRepository.save(user);
    }

    // 로그인 (JWT 발급)
    public String loginUser(LoginRequestDTO loginRequest) {
        Optional<User> optionalUser = userRepository.findByemail(loginRequest.email());
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        // JWT 발급
        return jwtUtil.generateToken(user.getEmail());
    }
}
