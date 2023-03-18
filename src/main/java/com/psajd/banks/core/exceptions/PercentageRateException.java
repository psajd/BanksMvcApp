package com.psajd.banks.core.exceptions;

/**
 * exceptions that may be caused when calculating interest rates
 */
public class PercentageRateException extends RuntimeException {

    public PercentageRateException() {
    }

    public PercentageRateException(String message) {
        super(message);
    }

    public PercentageRateException(String message, Throwable cause) {
        super(message, cause);
    }

    public PercentageRateException(Throwable cause) {
        super(cause);
    }

    public PercentageRateException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
