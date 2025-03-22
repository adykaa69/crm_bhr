package hu.bhr.crm.validation;

import hu.bhr.crm.exception.InvalidEmailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailValidationTest {

    @Test
    void testValidEmail() {
        assertDoesNotThrow(() -> EmailValidation.validate("text@example.com"));
    }

    @Test
    void testInvalidEmail() {
        assertThrows(InvalidEmailException.class, () -> EmailValidation.validate("invalid-email"));
    }

    @Test
    void testNullEmail() {
        assertDoesNotThrow(() -> EmailValidation.validate(null));
    }

}