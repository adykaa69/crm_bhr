package hu.bhr.crm.mapper;

import hu.bhr.crm.controller.dto.CustomerResponse;
import hu.bhr.crm.model.Customer;
import hu.bhr.crm.repository.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    // Entity -> Response DTO
    public Customer CustomerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(
                customerEntity.getId(),
                customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getNickname(),
                customerEntity.getEmail(),
                customerEntity.getPhoneNumber(),
                customerEntity.getRelationship(),
                customerEntity.getCreatedAt(),
                customerEntity.getUpdatedAt()
        );
    }

    // Customer -> Entity
    public CustomerEntity CustomerToCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setId(customer.id());
        customerEntity.setFirstName(customer.firstName());
        customerEntity.setLastName(customer.lastName());
        customerEntity.setNickname(customer.nickname());
        customerEntity.setEmail(customer.email());
        customerEntity.setPhoneNumber(customer.phoneNumber());
        customerEntity.setRelationship(customer.relationship());

        return customerEntity;
    }

    public CustomerResponse CustomerToCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.id(),
                customer.firstName(),
                customer.lastName(),
                customer.nickname(),
                customer.email(),
                customer.phoneNumber(),
                customer.relationship(),
                customer.createdAt(),
                customer.updatedAt()
        );
    }
}
