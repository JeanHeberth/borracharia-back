package br.com.borracharia.config;

import br.com.borracharia.entity.User;
import br.com.borracharia.enums.Role;
import br.com.borracharia.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;


@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        // Criar admin se não existir
        if (userRepository.findByUsername("Jean Heberth").isEmpty()) {
            User admin = User.builder()
                    .username("Jean Heberth")
                    .passwordHash(BCrypt.hashpw("admin123", BCrypt.gensalt()))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();

            userRepository.save(admin);
            System.out.println("✅ Usuário ADMIN criado: admin / admin123");
        }

        // Criar funcionário padrão se não existir
        if (userRepository.findByUsername("João Victor").isEmpty()) {
            User func = User.builder()
                    .username("João Victor")
                    .passwordHash(BCrypt.hashpw("func123", BCrypt.gensalt()))
                    .role(Role.FUNC)
                    .enabled(true)
                    .build();

            userRepository.save(func);
            System.out.println("✅ Usuário FUNC criado: func / func123");
        }
    }
}
