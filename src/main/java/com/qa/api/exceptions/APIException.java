package com.qa.api.exceptions;

/**
 * Custom exception class for API-related errors.
 */
public class APIException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor with an error message.
     *
     * @param msg Error message describing the exception.
     */
    public APIException(String msg) {
        super(msg);
    }

    /**
     * Constructor with an error message and a cause.
     *
     * @param msg   Error message describing the exception.
     * @param cause The underlying cause of the exception.
     */
    public APIException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Default constructor with a generic error message.
     */
    public APIException() {
        super("API Exception occurred");
    }
}