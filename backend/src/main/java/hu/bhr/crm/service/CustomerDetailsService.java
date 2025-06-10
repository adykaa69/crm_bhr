package hu.bhr.crm.service;

import hu.bhr.crm.exception.CustomerNotFoundException;
import hu.bhr.crm.mapper.CustomerDetailsMapper;
import hu.bhr.crm.model.CustomerDetails;
import hu.bhr.crm.repository.CustomerRepository;
import hu.bhr.crm.repository.mongo.CustomerDocumentRepository;
import hu.bhr.crm.repository.mongo.document.CustomerDocument;
import hu.bhr.crm.validation.FieldValidation;
import org.springframework.stereotype.Service;

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
     * Creates new customer details and stores them in the database.
     * Responds with 201 Created if the details are successfully created.
     *
     * @param customerDetails the built CustomerDetails containing the new customer details
     * @return the created {@link CustomerDetails} object
     * @throws CustomerNotFoundException if the customer with the given ID does not exist (returns HTTP 404 Not Found)
     * @throws hu.bhr.crm.exception.MissingFieldException if field "notes" is missing
     */
    public CustomerDetails saveCustomerDetails(CustomerDetails customerDetails) {
        FieldValidation.validateNotEmpty(customerDetails.notes(), "Field \"notes\"");

        if (!customerRepository.existsById(customerDetails.customerId())) {
            throw new CustomerNotFoundException("Customer not found");
        }

        CustomerDocument customerDocument = mapper.customerDetailsToCustomerDocument(customerDetails);
        CustomerDocument savedDocument = customerDocumentRepository.save(customerDocument);

        return mapper.customerDocumentToCustomerDetails(savedDocument);
    }
}
