package br.com.borracharia.service;

import br.com.borracharia.entity.Customer;
import br.com.borracharia.enums.Role;
import br.com.borracharia.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;


    public List<Customer> list(Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + Role.ADMIN.name()));
        if (isAdmin) return customerRepository.findAll();
        return customerRepository.findAllByCreatedBy(auth.getName());
    }

    public Customer getById(String id, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + Role.ADMIN.name()));
        return isAdmin ? customerRepository.findById(id).orElse(null) : customerRepository.findByIdAndCreatedBy(id, auth.getName()).orElse(null);
    }

    public Customer create(Customer c) { return customerRepository.save(c); }

    public Customer update(String id, Customer update, Authentication auth) {
        var existing = getById(id, auth);
        if (existing == null) return null;
        existing.setName(update.getName());
        existing.setPhone(update.getPhone());
        existing.setEmail(update.getEmail());
        return customerRepository.save(existing);
    }

    public boolean delete(String id, Authentication auth) {
        var existing = getById(id, auth);
        if (existing == null) return false;
        customerRepository.deleteById(existing.getId());
        return true;
    }
}
