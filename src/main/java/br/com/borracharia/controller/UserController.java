package br.com.borracharia.controller;

import br.com.borracharia.dto.CreateUserRequest;
import br.com.borracharia.dto.PasswordRequest;
import br.com.borracharia.dto.StatusRequest;
import br.com.borracharia.entity.User;
import br.com.borracharia.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // 🔒 Somente ADMIN pode listar todos os usuários
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> list() {
        return service.findAll();
    }

    // 🔒 Somente ADMIN pode criar usuários
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> create(@RequestBody CreateUserRequest req) {
        var user = service.createUser(req.username, req.password, req.role, true);
        return ResponseEntity.created(URI.create("/api/users/" + user.getId())).body(user);
    }

    // 🔒 Somente ADMIN pode resetar senha
    @PutMapping("/{username}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> resetPassword(@PathVariable String username, @RequestBody PasswordRequest req) {
        var user = service.updatePassword(username, req.newPassword);
        return ResponseEntity.ok(user);
    }

    // 🔒 Somente ADMIN pode ativar/desativar usuário
    @PutMapping("/{username}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> toggleEnabled(@PathVariable String username, @RequestBody StatusRequest req) {
        var user = service.toggleEnabled(username, req.enabled);
        return ResponseEntity.ok(user);
    }

    // 🔒 Somente ADMIN pode deletar usuário
    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        return service.deleteUser(username) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
