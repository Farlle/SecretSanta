package org.example.secretsanta.exception;

public class NotEnoughUsersException extends RuntimeException {
    public NotEnoughUsersException(String message) {
        super(message);
    }

}
