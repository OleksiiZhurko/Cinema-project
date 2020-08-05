package com.cinema.exceptions;

/**
 * Checked exception which means the movie session is absent
 */
public class MovieSessionNotFoundException extends Exception {

    public MovieSessionNotFoundException() {}

    public MovieSessionNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
