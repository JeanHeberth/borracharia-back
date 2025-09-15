package br.com.borracharia.repository;

import br.com.borracharia.entity.User;
import br.com.borracharia.entity.WorkOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);

}
