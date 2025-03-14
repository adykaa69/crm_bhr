package hu.bhr.crm.mapper;

import hu.bhr.crm.controller.dto.CustomerRequest;
import hu.bhr.crm.model.Customer;

import java.sql.Timestamp;
import java.util.UUID;

public class CustomerFactory {

    /**
     * Builds a Customer from a CustomerRequest.
     *
     * @param customerRequest the unique ID of the requested customer
     * @return one built customer in a {@link Customer}
     */
    public static Customer createCustomer(CustomerRequest customerRequest) {
        return Customer.builder()
                .id(UUID.randomUUID().toString())
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .nickname(customerRequest.nickname())
                .email(customerRequest.email())
                .phoneNumber(customerRequest.phoneNumber())
                .relationship((customerRequest.relationship()))
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
