package br.com.borracharia.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor
public class LoginRes {
    private String token;
    private String username;
    private String role;
}
