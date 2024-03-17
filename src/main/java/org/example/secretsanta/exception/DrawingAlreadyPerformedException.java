package org.example.secretsanta.exception;

public class DrawingAlreadyPerformedException extends RuntimeException {
    public DrawingAlreadyPerformedException(String message) {
        super(message);
    }
}