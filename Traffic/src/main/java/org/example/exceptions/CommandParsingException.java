package org.example.exceptions;

public class CommandParsingException extends RuntimeException {

    public CommandParsingException(String message) {
        super(message);
    }
}
