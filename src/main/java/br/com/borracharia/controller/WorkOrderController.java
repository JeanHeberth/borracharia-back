package br.com.borracharia.controller;

import br.com.borracharia.dto.worker.WorkOrderReq;
import br.com.borracharia.dto.worker.WorkOrderRes;
import br.com.borracharia.entity.WorkOrder;
import br.com.borracharia.mapper.worker.WorkOrderMapper;
import br.com.borracharia.service.WorkOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work-orders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    @GetMapping
    public List<WorkOrder> list(Authentication auth) {
        return workOrderService.list(auth);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderRes> get(@PathVariable String id, Authentication auth) {
        var wo = workOrderService.getById(id, auth);
        return wo != null ? ResponseEntity.ok(WorkOrderMapper.toDto(wo)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<WorkOrder> create(@Valid @RequestBody WorkOrderReq req, Authentication auth) {
        var saved = workOrderService.create(WorkOrderMapper.toEntity(req), auth);
        return ResponseEntity.created(URI.create("/api/work-orders/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> update(@PathVariable String id, @Valid @RequestBody WorkOrderReq req, Authentication auth) {
        var upd = workOrderService.update(id, WorkOrderMapper.toEntity(req), auth);
        return upd != null ? ResponseEntity.ok(upd) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, Authentication auth) {
        return workOrderService.delete(id, auth) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


}