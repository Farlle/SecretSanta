package ru.sberschool.secretsanta.exception;

/**
 * Кастомное исключение для жеребьевки если она была уже проведена
 */
public class DrawingAlreadyPerformedException extends RuntimeException {
    public DrawingAlreadyPerformedException(String message) {
        super(message);
    }

}