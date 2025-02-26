package hu.bhr.backend.customer.dto;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerDTO(
    @JsonProperty("id") String id,
    @JsonProperty("firstName") String firstName,
    @JsonProperty("lastName") String lastName,
    @JsonProperty("email") String email,
    @JsonProperty("phoneNumber") String phoneNumber
) {}
