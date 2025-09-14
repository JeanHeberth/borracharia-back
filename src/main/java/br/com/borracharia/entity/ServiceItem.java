package br.com.borracharia.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceItem {
    private String description;     // ex: "Troca de pneu"
    private int quantity;           // ex: 2
    private BigDecimal unitPrice;   // ex: 80.00
}
