package br.com.borracharia.mapper.user;

import br.com.borracharia.dto.user.CreateUserReq;
import br.com.borracharia.dto.user.CreateUserRes;
import br.com.borracharia.entity.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserMapper {

    // Converte DTO de criação -> Entity
    public static User toEntity(CreateUserReq dto) {
        return User.builder()
                .username(dto.getUsername())
                .passwordHash(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()))
                .role(dto.getRole())
                .enabled(true)
                .build();
    }

    // Converte Entity -> DTO de resposta
    public static CreateUserRes toDto(User entity) {
        return CreateUserRes.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .role(entity.getRole())
                .enabled(entity.isEnabled())
                .build();
    }
}
