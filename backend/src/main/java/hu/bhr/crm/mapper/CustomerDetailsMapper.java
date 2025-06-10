package hu.bhr.crm.mapper;

import hu.bhr.crm.controller.dto.CustomerDetailsRequest;
import hu.bhr.crm.controller.dto.CustomerDetailsResponse;
import hu.bhr.crm.model.CustomerDetails;
import hu.bhr.crm.repository.mongo.document.CustomerDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerDetailsMapper {
    CustomerDetails customerDocumentToCustomerDetails(CustomerDocument customerDocument);

    CustomerDocument customerDetailsToCustomerDocument(CustomerDetails customerDetails);

    CustomerDetailsResponse customerDetailsToCustomerDetailsResponse(CustomerDetails customerDetails);

    CustomerDetails customerDetailsRequestToCustomerDetails(CustomerDetailsRequest customerDetailsRequest);
}
