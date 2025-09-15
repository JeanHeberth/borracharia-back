package br.com.borracharia.dto.user;

import br.com.borracharia.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Role role;
}
