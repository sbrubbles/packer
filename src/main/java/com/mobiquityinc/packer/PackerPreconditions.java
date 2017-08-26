package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

/**
 * Precondition implementation for simple condition checking, resulting in an {@link APIException} in case of false conditions.
 */
public class PackerPreconditions {

    /**
     * Private empty constructor to avoid instantiations
     */
    private PackerPreconditions() {
    }

    /**
     * Verifies if a condition is true. If not, throws an {@link APIException}, containing the message given by parameter.
     *
     * @param condition the condition the be checked
     * @param message   the messaged to be added to the exception in case of false conditions
     * @param arguments the arguments of the message
     * @throws APIException in case of the condition not being equals to true
     */
    public static void checkCondition(boolean condition, String message, Object... arguments) {
        if (!condition) {
            throw new APIException(String.format(message, arguments));
        }
    }
}
