package br.com.borracharia.entity;

import br.com.borracharia.common.BaseAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends BaseAudit {
    @Id
    private String id;
    private String name;
    private String phone;
    private String email;
}