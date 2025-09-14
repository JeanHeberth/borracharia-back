package br.com.borracharia.service;

import br.com.borracharia.entity.User;
import br.com.borracharia.enums.Role;
import br.com.borracharia.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Criar novo usuário
    public User createUser(String username, String password, Role role, boolean enabled) {
        if (repo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Usuário já existe: " + username);
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = User.builder()
                .username(username)
                .passwordHash(passwordHash)
                .role(role)
                .enabled(enabled)
                .build();

        return repo.save(user);
    }

    // Buscar por username
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    // Buscar todos
    public List<User> findAll() {
        return repo.findAll();
    }

    // Atualizar senha
    public User updatePassword(String username, String newPassword) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + username));

        user.setPasswordHash(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        return repo.save(user);
    }

    // Ativar/Desativar usuário
    public User toggleEnabled(String username, boolean enabled) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + username));

        user.setEnabled(enabled);
        return repo.save(user);
    }

    // Deletar
    public boolean deleteUser(String username) {
        Optional<User> userOpt = repo.findByUsername(username);
        if (userOpt.isPresent()) {
            repo.delete(userOpt.get());
            return true;
        }
        return false;
    }
}