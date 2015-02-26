package com.company.cbess;

/**
 * Created by caseybrumbaugh on 2/26/15.
 *
 * Provides consistent exception for all Box APIs
 */
public class CompanyBoxException extends Exception {

    public CompanyBoxException() {
        super();
    }

    public CompanyBoxException(String message) {
        super(message);
    }

    public CompanyBoxException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyBoxException(Throwable cause) {
        super(cause);
    }
}
