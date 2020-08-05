package com.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.log4j.Logger;

/**
 * Main class
 */
@SpringBootApplication
public class WhiteBlackApp {

    /**
     * Entry point into the program
     * @param args array of some stringify values
     */
    public static void main(String[] args) {
        SpringApplication.run(WhiteBlackApp.class, args);
    }

}
