package br.com.borracharia.dto.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerReq {

    @NotBlank
    private String name;
    private String phone;
    private String email;
}
