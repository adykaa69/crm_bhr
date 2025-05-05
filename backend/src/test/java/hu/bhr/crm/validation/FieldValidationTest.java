package hu.bhr.crm.validation;

import hu.bhr.crm.exception.MissingFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldValidationTest {

    @Test
    void testValidField() {
        assertDoesNotThrow(() -> FieldValidation.validateNotEmpty("John Doe", "name"));
    }

    @Test
    void testEmptyField() {
        assertThrows(MissingFieldException.class, () -> FieldValidation.validateNotEmpty("", "name"));
    }

    @Test
    void testNullField() {
        assertThrows(MissingFieldException.class, () -> FieldValidation.validateNotEmpty(null, "name"));
    }

    @Test
    void testWhitespaceField() {
        assertThrows(MissingFieldException.class, () -> FieldValidation.validateNotEmpty("   ", "name"));
    }


    @Test
    void testFirstFieldPresentSecondEmpty() {
        assertDoesNotThrow(() -> FieldValidation.validateAtLeastOneIsNotEmpty("John", "First Name", "", "Nickname"));
    }

    @Test
    void testSecondFieldPresentFirstNull() {
        assertDoesNotThrow(() -> FieldValidation.validateAtLeastOneIsNotEmpty(null, "First Name", "Johnny", "Nickname"));
    }

    @Test
    void testBothFieldsPresent() {
        assertDoesNotThrow(() -> FieldValidation.validateAtLeastOneIsNotEmpty("John", "First Name", "Johnny", "Nickname"));
    }

    @Test
    void testBothFieldsEmpty() {
        assertThrows(MissingFieldException.class, () -> FieldValidation.validateAtLeastOneIsNotEmpty("", "First Name", "", "Nickname"));
    }

    @Test
    void testBothFieldsNull() {
        assertThrows(MissingFieldException.class, () -> FieldValidation.validateAtLeastOneIsNotEmpty(null, "First Name", null, "Nickname"));
    }
}