package hu.bhr.crm.controller.dto;

public record PlatformResponse(
    String status,
    String message,
    Object data
) {}
