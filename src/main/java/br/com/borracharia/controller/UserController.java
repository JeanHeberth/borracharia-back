package br.com.borracharia.controller;

import br.com.borracharia.dto.user.CreateUserReq;
import br.com.borracharia.dto.password.UpdatePasswordReq;
import br.com.borracharia.dto.status.StatusRequest;
import br.com.borracharia.dto.user.CreateUserRes;
import br.com.borracharia.mapper.user.UserMapper;
import br.com.borracharia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    // 🔒 Somente ADMIN pode listar todos os usuários
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<CreateUserRes> list() {
        return userService.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    // 🔒 Somente ADMIN pode criar usuários
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateUserRes> create(@RequestBody CreateUserReq req) {
        var user = userService.createUser(req);
        return ResponseEntity
                .created(URI.create("/api/users/" + user.getId()))
                .body(UserMapper.toDto(user));
    }

    // 🔒 Somente ADMIN pode resetar senha
    @PutMapping("/{username}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateUserRes> resetPassword(@PathVariable String username,
                                                       @RequestBody UpdatePasswordReq req) {
        var user = userService.updatePassword(username, req.getNewPassword());
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    // Usuário logado pode alterar a própria senha
    @PutMapping("/me/password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordReq req, Authentication auth) {
        userService.updateOwnPassword(auth.getName(), req.getOldPassword(), req.getNewPassword());
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }

    // 🔒 Somente ADMIN pode ativar/desativar usuário
    @PutMapping("/{username}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateUserRes> toggleEnabled(@PathVariable String username,
                                                       @RequestBody StatusRequest req) {
        var user = userService.toggleEnabled(username, req.isEnabled());
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    // 🔒 Somente ADMIN pode deletar usuário
    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        return userService.deleteUser(username)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
