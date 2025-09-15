package br.com.borracharia.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerReq {

    @NotBlank
    public String name;
    public String phone;
    public String email;
}
