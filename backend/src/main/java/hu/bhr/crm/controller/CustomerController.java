package hu.bhr.crm.controller;

import hu.bhr.crm.controller.dto.CustomerRequest;
import hu.bhr.crm.controller.dto.CustomerResponse;
import hu.bhr.crm.controller.dto.PlatformResponse;
import hu.bhr.crm.mapper.CustomerFactory;
import hu.bhr.crm.model.Customer;
import hu.bhr.crm.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Gets one customer by their unique ID.
     * Responses with 200 OK if the customer is responded with
     *
     * @param id the unique ID of the requested customer
     * @return one customer in a {@link PlatformResponse}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlatformResponse<CustomerResponse> getCustomer(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);

        return new PlatformResponse<>("success", "Customer retrieved successfully", customerResponse);
    }

    /**
     * Creates a new customer and stores it in the database.
     * Responses with 201 Created if the customer is successfully created
     *
     * @param customerRequest the data transfer object containing the new customer details
     * @return the created customer in a {@link PlatformResponse}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlatformResponse<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = CustomerFactory.createCustomer(customerRequest);
        CustomerResponse customerResponse = customerService.createCustomer(customer);

        return new PlatformResponse<>("success", "Customer created successfully", customerResponse);
    }

}

