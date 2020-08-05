package com.cinema.exceptions;

/**
 * Checked exception which means desired ticket was bought
 */
public class TicketAlreadyBoughtException extends Exception {

    public TicketAlreadyBoughtException() {}

    public TicketAlreadyBoughtException(String errorMessage) {
        super(errorMessage);
    }

}
