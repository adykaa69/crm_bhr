package hu.bhr.crm.exception;

import hu.bhr.crm.controller.dto.ErrorResponse;
import hu.bhr.crm.controller.dto.PlatformResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public PlatformResponse<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse("CUSTOMER.NOT_FOUND", ex.getMessage(), LocalDateTime.now());
        return new PlatformResponse<>("error", "Error occurred during requesting customer", errorResponse);
    }
}
