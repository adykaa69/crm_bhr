package hu.bhr.crm.controller.mapper;

import hu.bhr.crm.controller.dto.CustomerDTO;
import hu.bhr.crm.repository.entity.CustomerEntity;
import org.springframework.stereotype.Component;

//TBD - CustomerMapper needed?

@Component
public class CustomerMapper {

    // Entity -> DTO
    public CustomerDTO mapToCustomerDTO(CustomerEntity customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }

    // DTO -> Entity
    public CustomerEntity mapToCustomerEntity(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setId(customerDTO.id());
        customerEntity.setFirstName(customerDTO.firstName());
        customerEntity.setLastName(customerDTO.lastName());
        customerEntity.setEmail(customerDTO.email());
        customerEntity.setPhoneNumber(customerDTO.phoneNumber());

        return customerEntity;

    }
}
