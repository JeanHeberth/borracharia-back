package br.com.borracharia.mapper.customer;


import br.com.borracharia.dto.customer.CustomerReq;
import br.com.borracharia.dto.customer.CustomerRes;
import br.com.borracharia.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerReq dto) {
        return Customer.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
    }

    public static CustomerRes toDto(Customer entity) {
        CustomerRes dto = new CustomerRes();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setCreatedBy(entity.getCreatedBy()); // 👈 traz do BaseAudit
        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null);

        return dto;
    }
}
