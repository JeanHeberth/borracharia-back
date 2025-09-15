package br.com.borracharia.dto.user;

import br.com.borracharia.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRes {

    private String id;
    private String username;
    private Role role;
    private boolean enabled;

}
