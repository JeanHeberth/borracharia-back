package br.com.borracharia.mapper.login;

import br.com.borracharia.dto.login.LoginRes;
import br.com.borracharia.entity.User;

public class LoginMapper {

    public static LoginRes toDto(String token, User user) {
        return LoginRes.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}