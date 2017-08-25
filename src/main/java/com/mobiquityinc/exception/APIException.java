package com.mobiquityinc.exception;

/**
 * Application's default exception implementation, to indicate errors occured during it's execution.
 */
public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }

    public APIException(Exception cause) {
        super(cause);
    }
}
