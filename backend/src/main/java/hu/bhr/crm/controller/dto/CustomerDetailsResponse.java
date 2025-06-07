package hu.bhr.crm.controller.dto;

import java.time.Instant;
import java.util.UUID;

public record CustomerDetailsResponse(
        UUID id,
        UUID customerId,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {}
