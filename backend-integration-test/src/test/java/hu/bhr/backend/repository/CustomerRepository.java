package hu.bhr.backend.repository;

import hu.bhr.backend.repository.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Since DELETE request will handle data cleanup, ToBeDeleted?
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
}
