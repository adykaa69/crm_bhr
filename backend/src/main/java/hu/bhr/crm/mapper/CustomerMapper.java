package hu.bhr.crm.mapper;

import hu.bhr.crm.controller.dto.CustomerResponse;
import hu.bhr.crm.model.Customer;
import hu.bhr.crm.repository.entity.CustomerEntity;
import org.springframework.stereotype.Component;

//TBD - CustomerMapper needed?

@Component
public class CustomerMapper {

    // Entity -> Response DTO
    public CustomerResponse mapToCustomerResponse(CustomerEntity customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getNickname(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getRelationship(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    // Request DTO -> Entity
    public CustomerEntity mapToCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setId(customer.getId());
        customerEntity.setFirstName(customer.getFirstName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setNickname(customer.getNickname());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setPhoneNumber(customer.getPhoneNumber());
        customerEntity.setRelationship(customer.getRelationship());

        return customerEntity;
    }
}
