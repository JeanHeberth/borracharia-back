package br.com.borracharia.service;

import br.com.borracharia.entity.ServiceItem;
import br.com.borracharia.entity.WorkOrder;
import br.com.borracharia.enums.Role;
import br.com.borracharia.enums.WorkOrderStatus;
import br.com.borracharia.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository repo;


    // Listagem (Admin vê tudo, Func só os próprios)
    public List<WorkOrder> list(Authentication auth) {
        return isAdmin(auth) ? repo.findAll() : repo.findAllByCreatedBy(auth.getName());
    }

    // Buscar por ID
    public WorkOrder getById(String id, Authentication auth) {
        return isAdmin(auth)
                ? repo.findById(id).orElse(null)
                : repo.findByIdAndCreatedBy(id, auth.getName()).orElse(null);
    }

    // Criar nova OS (ADMIN e FUNC podem criar)
    public WorkOrder create(WorkOrder order, Authentication auth) {
        if (order.getServiceDate() == null) {
            order.setServiceDate(LocalDateTime.now());
        }
        if (order.getStatus() == null) {
            order.setStatus(WorkOrderStatus.OPEN);
        }
        order.setTotal(calculateTotal(order.getItems()));
        order.setCreatedBy(auth.getName()); // herdado de BaseAudit

        return repo.save(order);
    }

    // Atualizar (FUNC só atualiza as próprias)
    public WorkOrder update(String id, WorkOrder update, Authentication auth) {
        var existing = getById(id, auth);
        if (existing == null) return null;

        existing.setCustomerId(update.getCustomerId());
        existing.setPlate(update.getPlate());
        existing.setServiceDate(update.getServiceDate() != null ? update.getServiceDate() : existing.getServiceDate());
        existing.setItems(update.getItems());
        existing.setPaymentMethod(update.getPaymentMethod());
        existing.setStatus(update.getStatus() != null ? update.getStatus() : existing.getStatus());

        existing.setTotal(calculateTotal(update.getItems()));

        return repo.save(existing);
    }

    // Deletar
    public boolean delete(String id, Authentication auth) {
        var existing = getById(id, auth);
        if (existing == null) return false;
        repo.deleteById(existing.getId());
        return true;
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + Role.ADMIN.name()));
    }

    private BigDecimal calculateTotal(List<ServiceItem> items) {
        if (items == null || items.isEmpty()) return BigDecimal.ZERO;
        return items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
