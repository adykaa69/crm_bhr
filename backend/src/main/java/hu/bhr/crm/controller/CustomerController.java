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

    // GET
    // Get all Customers
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PlatformResponse getAllCustomers() {
        List<CustomerDTO> customers = repository.findAll().stream()
                .map(customerMapper::mapToCustomerDTO)
                .collect(Collectors.toList());

        return new PlatformResponse("success", "All Customers retrieved successfully", customers);
    }

    // GET
    // Get one Customer specified by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlatformResponse getCustomer(@PathVariable String id) {
        Optional<CustomerEntity> customerEntity = repository.findById(id);

        if (customerEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

            // Map DTO to Entity
            CustomerDTO customerDTO = customerMapper.mapToCustomerDTO(customerEntity.get());

            return new PlatformResponse("success", "Customer retrieved successfully", customerDTO);
    }

    // DELETE
    // Delete one Customer specified by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"); // throw exception needed?
        }

        repository.deleteById(id);
    }

    // POST
    // Create a new Customer
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlatformResponse createCustomer(@RequestBody CustomerDTO customerDTO) {

        // Map DTO to Entity
        CustomerEntity customerEntity = customerMapper.mapToCustomerEntity(customerDTO);

        // Save Entity to DB
        customerEntity = repository.save(customerEntity);

        // Response with saved Entity
        CustomerDTO savedCustomerDTO = customerMapper.mapToCustomerDTO(customerEntity);
        return new PlatformResponse("succes", "Customer created successfully", savedCustomerDTO);
    }

    // PUT
    // Overwrite an existing Customer with new data
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

    // PATCH
    // Update customer
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

