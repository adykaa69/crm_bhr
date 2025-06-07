package hu.bhr.crm.repository.mongo;

import hu.bhr.crm.repository.mongo.document.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CustomerDocumentRepository extends MongoRepository<CustomerDocument, UUID> {
}
