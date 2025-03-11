package hu.bhr.crm.controller.mapper;

import hu.bhr.crm.controller.dto.CustomerRequest;
import hu.bhr.crm.controller.dto.CustomerResponse;
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
                customer.getConnection(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    // Request DTO -> Entity
    public CustomerEntity mapToCustomerEntity(CustomerRequest customerRequest) {
        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setFirstName(customerRequest.firstName());
        customerEntity.setLastName(customerRequest.lastName());
        customerEntity.setNickname(customerRequest.nickname());
        customerEntity.setEmail(customerRequest.email());
        customerEntity.setPhoneNumber(customerRequest.phoneNumber());
        customerEntity.setConnection(customerRequest.connection());

        return customerEntity;

    }

}
