package hu.bhr.crm.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        String firstName,
        String lastName,
        String nickname,
        @Email(message = "Invalid email format") // Needed?
        String email,
        String phoneNumber,
        @NotBlank(message = "Relationship is required") // Needed?
        String relationship
) {}