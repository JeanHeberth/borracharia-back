package br.com.borracharia.entity;

import br.com.borracharia.common.BaseAudit;
import br.com.borracharia.enums.PaymentMethod;
import br.com.borracharia.enums.WorkOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document("work_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder extends BaseAudit {
    @Id
    private String id;

    private String customerId;          // referência ao Customer
    private String plate;               // placa do carro
    private LocalDateTime serviceDate;  // data do serviço
    private List<ServiceItem> items;    // itens (N serviços)
    private BigDecimal total;           // total calculado
    private PaymentMethod paymentMethod;
    private WorkOrderStatus status;
}
