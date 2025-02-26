package hu.bhr.crm.controller.dto;

public record CustomerDTO(
   String id,
   String firstName,
   String lastName,
   String email,
   String phoneNumber
) {}
