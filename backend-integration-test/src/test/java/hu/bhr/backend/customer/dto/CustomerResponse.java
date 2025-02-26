package hu.bhr.backend.customer.dto;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerResponse<T>(
    @JsonProperty("status") String status,
    @JsonProperty("message") String message,
    @JsonProperty("data") T data
) {}
