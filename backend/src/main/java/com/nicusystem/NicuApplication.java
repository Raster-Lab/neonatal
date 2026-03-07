package com.nicusystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the NICU Management System.
 */
@SpringBootApplication
public class NicuApplication {

    /**
     * Starts the NICU application.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(NicuApplication.class, args);
    }
}
