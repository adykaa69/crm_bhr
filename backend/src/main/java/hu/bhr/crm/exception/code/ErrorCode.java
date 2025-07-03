package hu.bhr.crm.exception.code;

public enum ErrorCode {
    CUSTOMER_NOT_FOUND("CUSTOMER.NOT_FOUND"),
    CUSTOMER_DETAILS_NOT_FOUND("CUSTOMER.DETAILS.NOT_FOUND"),
    EMAIL_INVALID("EMAIL.INVALID"),
    MISSING_FIELD("FIELD.MISSING"),
    INVALID_STATUS("STATUS.INVALID"),
    INTERNAL_SERVER_ERROR("INTERNAL.SERVER_ERROR");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
