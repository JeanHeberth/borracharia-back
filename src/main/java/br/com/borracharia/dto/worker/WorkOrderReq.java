package br.com.borracharia.dto.worker;

import br.com.borracharia.entity.ServiceItem;
import br.com.borracharia.enums.PaymentMethod;
import br.com.borracharia.enums.WorkOrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkOrderReq {

    @NotNull
    private String customerId;
    @NotNull
    private String plate;
    private LocalDateTime serviceDate;
    private List<ServiceItem> items;
    private PaymentMethod paymentMethod;
    private WorkOrderStatus status;

    public BigDecimal calculateTotal() {
        if (items == null || items.isEmpty()) return BigDecimal.ZERO;
        return items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
