package com.cinema.exceptions;

/**
 * Checked exception which means mismatch prices
 */
public class PriceChangeException extends Exception {

    public PriceChangeException() {}

    public PriceChangeException(String errorMessage) {
        super(errorMessage);
    }

}
