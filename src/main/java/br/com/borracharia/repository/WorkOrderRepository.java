package br.com.borracharia.repository;

import br.com.borracharia.entity.WorkOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WorkOrderRepository extends MongoRepository<WorkOrder,String> {
    List<WorkOrder> findAllByCreatedBy(String createdBy);
    Optional<WorkOrder> findByIdAndCreatedBy(String id, String createdBy);
}
