package hu.bhr.crm.validation;

import hu.bhr.crm.exception.MissingFieldException;

public class FieldValidation {

    private FieldValidation() {

    }

    public static void validateNotEmpty(String field, String fieldName) {
        if (field == null || field.trim().isEmpty()) {
            throw new MissingFieldException(fieldName + " is required.");
        }
    }
}
