package br.com.borracharia.controller;

import br.com.borracharia.config.JwtUtil;
import br.com.borracharia.dto.login.LoginReq;
import br.com.borracharia.dto.login.LoginRes;
import br.com.borracharia.mapper.login.LoginMapper;
import br.com.borracharia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwt;


    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq req) {
        var u = userRepository.findByUsername(req.getUsername()).orElse(null);
        if (u == null || !u.isEnabled() || !BCrypt.checkpw(req.getPassword(), u.getPasswordHash())) {
            return ResponseEntity.status(401).build();
        }

        var token = jwt.generate(u.getUsername(), u.getRole().name());
        return ResponseEntity.ok(LoginMapper.toDto(token, u));
    }

}