package com.finance.trading.exception;

public class InternalException extends RuntimeException {
    public InternalException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
