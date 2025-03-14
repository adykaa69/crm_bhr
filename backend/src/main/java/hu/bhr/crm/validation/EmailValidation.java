package hu.bhr.crm.validation;

import hu.bhr.crm.exception.InvalidEmailException;

public class EmailValidation {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    private EmailValidation() {
    }

    public static void validate(String email) {
        if (email != null && !email.matches(EMAIL_REGEX)) {
            throw new InvalidEmailException("Invalid email format");
        }
    }
}
