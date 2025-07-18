package hu.bhr.crm.mapper;

import hu.bhr.crm.controller.dto.CustomerDetailsRequest;
import hu.bhr.crm.controller.dto.CustomerDetailsResponse;
import hu.bhr.crm.model.CustomerDetails;
import hu.bhr.crm.repository.mongo.document.CustomerDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerDetailsMapper {
    CustomerDetails customerDocumentToCustomerDetails(CustomerDocument customerDocument);

    CustomerDocument customerDetailsToCustomerDocument(CustomerDetails customerDetails);

    CustomerDetailsResponse customerDetailsToCustomerDetailsResponse(CustomerDetails customerDetails);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CustomerDetails customerDetailsRequestToCustomerDetails(UUID id, CustomerDetailsRequest customerDetailsRequest);
}
