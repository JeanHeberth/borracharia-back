package br.com.borracharia.controller;

import br.com.borracharia.config.JwtUtil;
import br.com.borracharia.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository users;
    private final JwtUtil jwt;

    public AuthController(UserRepository users, JwtUtil jwt) { this.users = users; this.jwt = jwt; }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req){
        var u = users.findByUsername(req.username).orElse(null);
        if (u == null || !u.isEnabled() || !BCrypt.checkpw(req.password, u.getPasswordHash()))
            return ResponseEntity.status(401).build();
        var token = jwt.generate(u.getUsername(), u.getRole().name());
        return ResponseEntity.ok(new TokenRes(token));
    }

    @Data
    static class LoginReq { @NotBlank
    String username; @NotBlank String password; }
    @Data static class TokenRes { private final String token; }
}