package hu.bhr.crm.controller.dto;

import java.util.UUID;

public record CustomerDetailsRequest(
        UUID customerId,
        String notes
) {}
