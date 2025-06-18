package hu.bhr.crm.service;

import hu.bhr.crm.exception.CustomerNotFoundException;
import hu.bhr.crm.mapper.CustomerDetailsMapper;
import hu.bhr.crm.model.CustomerDetails;
import hu.bhr.crm.repository.CustomerRepository;
import hu.bhr.crm.repository.mongo.CustomerDocumentRepository;
import hu.bhr.crm.repository.mongo.document.CustomerDocument;
import hu.bhr.crm.validation.FieldValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerDetailsService {

    private final CustomerDocumentRepository customerDocumentRepository;
    private final CustomerDetailsMapper mapper;
    private final CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerDocumentRepository customerDocumentRepository, CustomerDetailsMapper mapper, CustomerRepository customerRepository) {
        this.customerDocumentRepository = customerDocumentRepository;
        this.mapper = mapper;
        this.customerRepository = customerRepository;
    }

    /**
     * Gets customer details by their unique ID.
     *
     * @param id the unique ID of the requested customer details
     * @return a {@link CustomerDetails} object corresponding to the given ID
     * @throws CustomerNotFoundException if the customer with the given ID does not exist (returns HTTP 404 Not Found)
     */
    public CustomerDetails getCustomerDetailsById(UUID id) {
        CustomerDocument customerDocument = customerDocumentRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer details not found"));

        return mapper.customerDocumentToCustomerDetails(customerDocument);
    }

    /**
     * Gets all customer details for a specific customer by their unique ID.
     *
     * @param customerId the unique ID of the customer whose details are requested
     * @return a list of {@link CustomerDetails} objects corresponding to the given customer ID
     */
    public List<CustomerDetails> getAllCustomerDetails(UUID customerId) {
        return customerDocumentRepository.findAllByCustomerId(customerId).stream()
                .map(mapper::customerDocumentToCustomerDetails)
                .toList();
    }

    /**
     * Creates new customer details and stores them in the database.
     *
     * @param customerDetails the built CustomerDetails containing the new customer details
     * @return the created {@link CustomerDetails} object
     * @throws CustomerNotFoundException if the customer with the given ID does not exist (returns HTTP 404 Not Found)
     * @throws hu.bhr.crm.exception.MissingFieldException if field "note" is missing
     */
    public CustomerDetails saveCustomerDetails(CustomerDetails customerDetails) {
        FieldValidation.validateNotEmpty(customerDetails.note(), "Note");

        if (!customerRepository.existsById(customerDetails.customerId())) {
            throw new CustomerNotFoundException("Customer not found");
        }

        CustomerDocument customerDocument = mapper.customerDetailsToCustomerDocument(customerDetails);
        CustomerDocument savedDocument = customerDocumentRepository.save(customerDocument);

        return mapper.customerDocumentToCustomerDetails(savedDocument);
    }

    public void deleteCustomerDetailsByCustomerId(UUID customerId) {
        // Delete all documents related a customer
        customerDocumentRepository.deleteAllByCustomerId(customerId);
    }
}
