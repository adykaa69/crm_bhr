package hu.bhr.crm.controller;

import hu.bhr.crm.controller.dto.CustomerDTO;
import hu.bhr.crm.controller.dto.PlatformResponse;
import hu.bhr.crm.controller.mapper.CustomerMapper;
import hu.bhr.crm.repository.CustomerRepository;
import hu.bhr.crm.repository.entity.CustomerEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerRepository repository;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerRepository repository, CustomerMapper customerMapper) {
        this.repository = repository;
        this.customerMapper = customerMapper;
    }

    /**
     * Gets all costumers
     *
     * @return all customers in a {@link PlatformResponse}
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PlatformResponse getAllCustomers() {
        List<CustomerDTO> customers = repository.findAll().stream()
                .map(customerMapper::mapToCustomerDTO)
                .collect(Collectors.toList());

        return new PlatformResponse("success", "All Customers retrieved successfully", customers);
    }

    /**
     * Gets one customer by their unique ID.
     *
     * @param id the unique ID of the requested customer
     * @throws ResponseStatusException if the customer with the given ID does not exist (returns HTTP 404 Not Found)
     * @return one customer in a {@link PlatformResponse}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlatformResponse getCustomer(@PathVariable String id) {
        CustomerDTO customerDTO = repository.findById(id)
                .map(customerMapper::mapToCustomerDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

//        if (customerEntity.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
//        }
//
//            // Map DTO to Entity
//            CustomerDTO customerDTO = customerMapper.mapToCustomerDTO(customerEntity.get());

            return new PlatformResponse("success", "Customer retrieved successfully", customerDTO);
    }

    /**
     * Deletes a customer by their unique ID.
     * Returns 204 No Content if the deletion is successful.
     *
     * @param id the unique ID of the customer to be deleted
     * @throws ResponseStatusException if the customer with the given ID does not exist (returns HTTP 404 Not Found)
     *
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"); // throw exception needed?
        }

        repository.deleteById(id);
    }

    /**
     * Creates a new customer and stores it in the database.
     *
     * @param customerDTO the data transfer object containing the new customer details
     * @return the created customer in a {@link PlatformResponse}
     * @ResponseStatus 201 Created if the customer is successfully created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlatformResponse createCustomer(@RequestBody CustomerDTO customerDTO) {

        // Map DTO to Entity
        CustomerEntity customerEntity = customerMapper.mapToCustomerEntity(customerDTO);

        // Save Entity to DB
        customerEntity = repository.save(customerEntity);

        // Response with saved Entity
        CustomerDTO savedCustomerDTO = customerMapper.mapToCustomerDTO(customerEntity);
        return new PlatformResponse("success", "Customer created successfully", savedCustomerDTO);
    }

    /**
     * Overwrites an existing customer with the specified ID.
     *
     * @param id the unique ID of the customer to be overwritten
     * @param customerDTO the data transfer object containing the updated customer details
     * @return the updated customer in a {@link PlatformResponse}
     * @throws ResponseStatusException if the customer with the given ID does not exist (returns HTTP 404 Not Found)
     * @ResponseStatus 200 OK if the update is successful
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlatformResponse overwriteCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        Optional<CustomerEntity> customerEntityOptional = repository.findById(id);

        if (customerEntityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"); // throw exception needed?
        }

        CustomerEntity customerEntity = customerEntityOptional.get();

        // Overwrite data
        customerEntity.setId(id);
        customerEntity.setFirstName(customerDTO.firstName());
        customerEntity.setLastName(customerDTO.lastName());
        customerEntity.setEmail(customerDTO.email());
        customerEntity.setPhoneNumber(customerDTO.phoneNumber());

        // Save Entity to DB
        repository.save(customerEntity);

        // Response with overwritten Entity
        CustomerDTO updatedCustomerDTO = customerMapper.mapToCustomerDTO(customerEntity);
        return new PlatformResponse("success", "Customer updated successfully", updatedCustomerDTO);
    }

    /**
     * Partially updates an existing customer with the specified ID.
     * Only the fields provided in the {@code customerDTO} will be updated
     *
     * @param id the unique ID of the customer to be overwritten
     * @param customerDTO the data transfer object containing the customer details to update
     * @return the updated customer in a {@link PlatformResponse}
     * @throws ResponseStatusException if the customer with the given ID does not exist (returns HTTP 404 Not Found)
     * @ResponseStatus 200 OK if the update is successful
     */
    // ToDo once service and repository layers are done
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlatformResponse updateCustomer(@PathVariable String id,@RequestBody CustomerDTO customerDTO) {
        Optional<CustomerEntity> customerEntityOptional = repository.findById(id);

        if (customerEntityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"); // throw exception needed?
        }

        CustomerEntity customerEntity = customerEntityOptional.get();

        // Update only the fields that are given in DTO
        if (customerDTO.firstName() != null) {
            customerEntity.setFirstName(customerDTO.firstName());
        }
        if (customerDTO.lastName() != null) {
            customerEntity.setLastName(customerDTO.lastName());
        }
        if (customerDTO.email() != null) {
            customerEntity.setEmail(customerDTO.email());
        }
        if (customerDTO.phoneNumber() != null) {
            customerEntity.setPhoneNumber(customerDTO.phoneNumber());
        }

        // Save Entity to DB
        repository.save(customerEntity);

        // Response with overwritten Entity
        CustomerDTO updatedCustomerDTO = customerMapper.mapToCustomerDTO(customerEntity);
        return new PlatformResponse("success", "Customer updated successfully", updatedCustomerDTO);
    }
}

