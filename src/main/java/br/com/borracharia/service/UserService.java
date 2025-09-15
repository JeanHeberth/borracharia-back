package br.com.borracharia.service;

import br.com.borracharia.dto.user.CreateUserReq;
import br.com.borracharia.entity.User;
import br.com.borracharia.mapper.user.UserMapper;
import br.com.borracharia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Criar novo usuário
    public User createUser(CreateUserReq req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Usuário já existe: " + req.getUsername());
        }
        var user = UserMapper.toEntity(req); // já faz o hash da senha
        return userRepository.save(user);
    }

    // Buscar por username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Buscar todos
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Atualizar senha (ADMIN reseta)
    public User updatePassword(String username, String newPassword) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + username));

        user.setPasswordHash(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        return userRepository.save(user);
    }

    // Atualizar senha do próprio usuário
    public User updateOwnPassword(String username, String oldPassword, String newPassword) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + username));

        if (!BCrypt.checkpw(oldPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        user.setPasswordHash(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        return userRepository.save(user);
    }

    // Ativar/Desativar usuário
    public User toggleEnabled(String username, boolean enabled) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + username));

        user.setEnabled(enabled);
        return userRepository.save(user);
    }

    // Deletar usuário
    public boolean deleteUser(String username) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }
}
