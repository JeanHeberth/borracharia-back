package br.com.borracharia.controller;

import br.com.borracharia.dto.CustomerReq;
import br.com.borracharia.entity.Customer;
import br.com.borracharia.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> list(Authentication auth) {
        return service.list(auth);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable String id, Authentication auth) {
        var c = service.getById(id, auth);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody CustomerReq req) {
        var saved = service.create(Customer.builder().name(req.name).phone(req.phone).email(req.email).build());
        return ResponseEntity.created(URI.create("/api/customers/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable String id, @Valid @RequestBody CustomerReq req, Authentication auth) {
        var upd = service.update(id, Customer.builder().name(req.name).phone(req.phone).email(req.email).build(), auth);
        return upd != null ? ResponseEntity.ok(upd) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, Authentication auth) {
        return service.delete(id, auth) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


}