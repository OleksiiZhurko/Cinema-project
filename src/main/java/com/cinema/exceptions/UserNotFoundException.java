package com.cinema.exceptions;

/**
 * Checked exception which means absent of desired user
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException() {}

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
