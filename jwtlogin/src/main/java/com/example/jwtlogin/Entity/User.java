package com.example.jwtlogin.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // DB 테이블 이름
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @Column(nullable = false, unique = true, length = 100)
    private String email; // 로그인 아이디

    @Column(nullable = false, length = 255)
    private String password; // 암호화 저장

    @Column(nullable = false, length = 50)
    private String nickname; // 닉네임


    // ✅ JPA 기본 생성자 (필수)
    protected User() {}

    // ✅ 편의 생성자
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    // ✅ Getter / Setter
    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

}
