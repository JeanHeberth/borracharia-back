package br.com.borracharia.controller;

import br.com.borracharia.entity.ServiceItem;
import br.com.borracharia.entity.WorkOrder;
import br.com.borracharia.enums.PaymentMethod;
import br.com.borracharia.enums.WorkOrderStatus;
import br.com.borracharia.service.WorkOrderService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {
    private final WorkOrderService service;

    public WorkOrderController(WorkOrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<WorkOrder> list(Authentication auth) {
        return service.list(auth);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> get(@PathVariable String id, Authentication auth) {
        var wo = service.getById(id, auth);
        return wo != null ? ResponseEntity.ok(wo) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<WorkOrder> create(@Valid @RequestBody WorkOrderReq req, Authentication auth) {
        var saved = service.create(req.toEntity(), auth);
        return ResponseEntity.created(URI.create("/api/work-orders/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> update(@PathVariable String id, @Valid @RequestBody WorkOrderReq req, Authentication auth) {
        var upd = service.update(id, req.toEntity(), auth);
        return upd != null ? ResponseEntity.ok(upd) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, Authentication auth) {
        return service.delete(id, auth) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    @Data
    public static class WorkOrderReq {
        String customerId;
        String plate;
        LocalDateTime serviceDate;
        List<ServiceItem> items;
        PaymentMethod paymentMethod;
        WorkOrderStatus status;

        public WorkOrder toEntity() {
            var total = items == null ? BigDecimal.ZERO :
                    items.stream().map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
            return WorkOrder.builder()
                    .customerId(customerId)
                    .plate(plate)
                    .serviceDate(serviceDate == null ? LocalDateTime.now() : serviceDate)
                    .items(items)
                    .paymentMethod(paymentMethod)
                    .status(status == null ? WorkOrderStatus.OPEN : status)
                    .total(total)
                    .build();
        }
    }
}