package br.com.borracharia.dto.password;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordReq {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
