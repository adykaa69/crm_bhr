package hu.bhr.crm.exception;

public enum ErrorCode {
    CUSTOMER_NOT_FOUND("CUSTOMER.NOT_FOUND"),
    EMAIL_INVALID("EMAIL.INVALID"),
    EMAIL_EMPTY("EMAIL.INVALID.EMPTY_FIELD"),
    MISSING_FIELD("FIELD.MISSING");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
