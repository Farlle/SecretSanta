package org.example.secretsanta.exception;

/**
 * Кастомное исключение если пользователь уже существует
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

}