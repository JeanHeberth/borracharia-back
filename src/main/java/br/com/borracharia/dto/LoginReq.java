package br.com.borracharia.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginReq {

    @NotBlank
    public String username;
    @NotBlank
    public String password;
}
