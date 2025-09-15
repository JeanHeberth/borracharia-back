package br.com.borracharia.controller;

import br.com.borracharia.dto.customer.CustomerReq;
import br.com.borracharia.dto.customer.CustomerRes;
import br.com.borracharia.mapper.customer.CustomerMapper;
import br.com.borracharia.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerRes> list(Authentication auth) {
        return customerService.list(auth).stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerRes> get(@PathVariable String id, Authentication auth) {
        var c = customerService.getById(id, auth);
        return c != null ? ResponseEntity.ok(CustomerMapper.toDto(c)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerRes> create(@Valid @RequestBody CustomerReq req, Authentication auth) {
        var entity = CustomerMapper.toEntity(req);
        entity.setCreatedBy(auth.getName()); // herdado de BaseAudit
        var saved = customerService.create(entity);
        return ResponseEntity
                .created(URI.create("/api/customers/" + saved.getId()))
                .body(CustomerMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerRes> update(@PathVariable String id,
                                              @Valid @RequestBody CustomerReq req,
                                              Authentication auth) {
        var upd = customerService.update(id, CustomerMapper.toEntity(req), auth);
        return upd != null ? ResponseEntity.ok(CustomerMapper.toDto(upd)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, Authentication auth) {
        return customerService.delete(id, auth) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
