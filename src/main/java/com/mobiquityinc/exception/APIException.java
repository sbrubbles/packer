package com.mobiquityinc.exception;

/**
 * Application's default exception implementation, to indicate errors occurred during it's execution.
 */
public class APIException extends RuntimeException {
    /**
     * Instantiates a new APIException with a message
     *
     * @param message the message to be added to the exception
     */
    public APIException(String message) {
        super(message);
    }

    /**
     * Instantiates a new APIException with a cause exception
     *
     * @param cause the cause to be adder to the exception
     */
    public APIException(Exception cause) {
        super(cause);
    }
}
