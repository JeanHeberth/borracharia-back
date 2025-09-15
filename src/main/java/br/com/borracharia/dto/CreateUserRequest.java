package br.com.borracharia.dto;

import br.com.borracharia.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank
    public String username;
    @NotBlank
    public String password;
    public Role role;
}
