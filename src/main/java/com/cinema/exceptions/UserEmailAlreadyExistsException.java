package com.cinema.exceptions;

/**
 * Checked exception which means the email already exists
 */
public class UserEmailAlreadyExistsException extends Exception {

    public UserEmailAlreadyExistsException() {}

    public UserEmailAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

}
