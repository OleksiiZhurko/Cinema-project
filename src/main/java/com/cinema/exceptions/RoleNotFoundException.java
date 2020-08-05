package com.cinema.exceptions;

/**
 * Checked exception which means absent of desired role
 */
public class RoleNotFoundException extends Exception {

    public RoleNotFoundException() {}

    public RoleNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
