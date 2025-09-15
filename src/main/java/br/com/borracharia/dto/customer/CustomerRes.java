package br.com.borracharia.dto.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class CustomerRes {

    @NotBlank
    private String id;
    private String name;
    private String phone;
    private String email;
    private String createdBy;
    private String createdAt;
}
