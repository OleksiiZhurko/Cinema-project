package com.cinema.exceptions;

/**
 * Checked exception which means the user login already exists
 */
public class UserLoginAlreadyExistsException extends Exception {

    public UserLoginAlreadyExistsException() {}

    public UserLoginAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

}

