package org.example.exceptions;

public class DatabaseUnavailableException extends Exception {
    public DatabaseUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
