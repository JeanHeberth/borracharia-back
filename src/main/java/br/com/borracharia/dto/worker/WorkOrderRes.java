package br.com.borracharia.dto.worker;

import br.com.borracharia.entity.ServiceItem;
import br.com.borracharia.enums.PaymentMethod;
import br.com.borracharia.enums.WorkOrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class WorkOrderRes {

    private String id;
    private String customerId;
    private String plate;
    private LocalDateTime serviceDate;
    private List<ServiceItem> items;
    private PaymentMethod paymentMethod;
    private WorkOrderStatus status;
    private BigDecimal total;
    private String createdBy;
}
