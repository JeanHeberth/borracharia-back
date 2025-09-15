package br.com.borracharia.mapper.password;

import br.com.borracharia.dto.password.RegisterPasswordReq;
import br.com.borracharia.entity.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserMapper {

    public static User fromRegisterReq(RegisterPasswordReq dto) {
        return User.builder()
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .role(dto.getRole())
                .enabled(true)
                .passwordHash(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()))
                .build();
    }
}
