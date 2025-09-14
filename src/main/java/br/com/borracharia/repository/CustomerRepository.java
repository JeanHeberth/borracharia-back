package br.com.borracharia.repository;

import br.com.borracharia.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer,String> {
    List<Customer> findAllByCreatedBy(String createdBy);
    Optional<Customer> findByIdAndCreatedBy(String id, String createdBy);
}
